package hospital_management_sytem.Service;


import hospital_management_sytem.Dto.Billing.BillingRequestDto;
import hospital_management_sytem.Dto.Billing.BillingResponseDto;
import hospital_management_sytem.Dto.Billing.MakePaymentDto;
import hospital_management_sytem.Entity.AppointmentEntity;
import hospital_management_sytem.Entity.Billing.BillingEntity;
import hospital_management_sytem.Entity.Billing.BillingMedicineEntity;
import hospital_management_sytem.Entity.MedicineEntity;
import hospital_management_sytem.Entity.Prescription.PrescriptionEntity;
import hospital_management_sytem.Entity.Prescription.PrescriptionMedicineEntity;
import hospital_management_sytem.Entity.Profile.DeskStaffProfile;
import hospital_management_sytem.Entity.Profile.DoctorProfile;
import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Enum.BillStatus;
import hospital_management_sytem.Enum.GlobalRole;
import hospital_management_sytem.Enum.PaymentMode;
import hospital_management_sytem.Enum.PaymentStatus;
import hospital_management_sytem.Exeption.ResourceNotAvailable;
import hospital_management_sytem.Exeption.SomethingIsWrong;
import hospital_management_sytem.Exeption.UserNotFound;
import hospital_management_sytem.Mapping.Billing.BillingMapper;
import hospital_management_sytem.Repository.AppointmentRepository;
import hospital_management_sytem.Repository.Billing.BillingRepository;
import hospital_management_sytem.Repository.Prescription.PrescriptionRepository;
import hospital_management_sytem.Repository.ProfileRepository.DoctorProfileRepository;
import hospital_management_sytem.Repository.ProfileRepository.StaffRepository;
import hospital_management_sytem.Repository.UserRepository;
import hospital_management_sytem.Util.BillingNumberGenerate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class BillingService {

    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final BillingRepository billingRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final DoctorProfileRepository doctorProfileRepository;

    public BillingService(
            AppointmentRepository appointmentRepository,
            PrescriptionRepository prescriptionRepository,
            BillingRepository billingRepository,
            UserRepository userRepository,
            StaffRepository staffRepository,
            DoctorProfileRepository doctorProfileRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.billingRepository = billingRepository;
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.doctorProfileRepository = doctorProfileRepository;
    }

    // ======================================================
    // CREATE BILL
    // ======================================================
    @Transactional
    public BillingResponseDto createBill(Authentication auth,
                                         BillingRequestDto dto) {

        UserEntity user = getUser(auth);
        validateStaff(user);

        AppointmentEntity appointment = findAppointment(dto.getAppointmentId());

        if (billingRepository.existsByAppointment_Id(dto.getAppointmentId())) {
            throw new SomethingIsWrong("Bill already exists for this appointment");
        }

        BillingEntity bill = buildBaseBill(appointment);
        addMedicinesToBill(dto.getPrescriptionId(), appointment, bill);

        BillingEntity saved = billingRepository.save(bill);
        return BillingMapper.toDto(saved);
    }

    // ======================================================
    // PAYMENT
    // ======================================================
    @Transactional
    public void makePayment(Authentication auth, MakePaymentDto mode) {

        UserEntity user = getUser(auth);
        validateStaff(user);

        BillingEntity bill = findBill(mode.getBillingId());

        if (bill.getStatus() == BillStatus.VOID)
            throw new SomethingIsWrong("Bill is void");

        if (bill.getPaymentStatus() == PaymentStatus.PAID)
            throw new SomethingIsWrong("Bill already paid");


        bill.setPaymentStatus(PaymentStatus.PAID);
        bill.setPaymentMode(mode.getPaymentMode());


        billingRepository.save(bill);
    }

    // ======================================================
    // REGENERATE BILL
    // ======================================================
    @Transactional
    public BillingResponseDto regenerateBill(Authentication auth,
                                             Long billId,
                                             BillingRequestDto dto) {

        UserEntity user = getUser(auth);
        validateStaff(user);

        BillingEntity oldBill = findBill(billId);

        if (oldBill.getPaymentStatus() != PaymentStatus.UNPAID)
            throw new SomethingIsWrong("Cannot regenerate paid bill");

        oldBill.setStatus(BillStatus.VOID);
        oldBill.setVoidReason("Regenerated");
        oldBill.setVoidTime(LocalDateTime.now());

        BillingEntity newBill = buildBaseBill(oldBill.getAppointment());
        addMedicinesToBill(dto.getPrescriptionId(),
                oldBill.getAppointment(),
                newBill);

        return BillingMapper.toDto(billingRepository.save(newBill));
    }

    // ======================================================
    // FETCH BILL
    // ======================================================
    @Transactional(readOnly = true)
    public BillingResponseDto getBill(Authentication auth, Long billId) {

        UserEntity user = getUser(auth);
        BillingEntity bill = findBill(billId);

        if (!bill.getAppointment()
                .getPatientProfile()
                .getUser()
                .getId()
                .equals(user.getId())) {
            throw new SomethingIsWrong("Unauthorized access");
        }

        if (bill.getStatus() == BillStatus.VOID)
            throw new SomethingIsWrong("Bill is void");

        return BillingMapper.toDto(bill);
    }

    // ======================================================
    // INTERNAL METHODS
    // ======================================================

    private BillingEntity buildBaseBill(AppointmentEntity appointment) {

        BillingEntity bill = new BillingEntity();
        bill.setAppointment(appointment);
        bill.setDoctorProfile(appointment.getDoctorProfile());
        bill.setPatientProfile(appointment.getPatientProfile());

        bill.setConsultationFee(
                BigDecimal.valueOf(
                        appointment.getDoctorProfile().getConsultationFee()
                )
        );

        bill.setStatus(BillStatus.ACTIVE);
        bill.setPaymentStatus(PaymentStatus.UNPAID);

        return bill;
    }

    private void addMedicinesToBill(Long prescriptionId,
                                    AppointmentEntity appointment,
                                    BillingEntity bill) {

        PrescriptionEntity prescription = prescriptionRepository
                .findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotAvailable("Prescription not found"));

        if (!prescription.getAppointmentEntity().getId().equals(appointment.getId()))
            throw new SomethingIsWrong("Prescription does not belong to appointment");

        List<BillingMedicineEntity> medicines = new ArrayList<>();
        BigDecimal medicineTotal = BigDecimal.ZERO;

        for (PrescriptionMedicineEntity pm : prescription.getPrescriptionMedicineEntity()) {

            BigDecimal total =
                    pm.getMedicineEntity().getPrice()
                            .multiply(BigDecimal.valueOf(pm.getQuantity()));

            BillingMedicineEntity bm = new BillingMedicineEntity();
            bm.setMedicineCode(pm.getMedicineEntity().getMedicineCode());
            bm.setMedicineName(pm.getMedicineEntity().getName());
            bm.setBrandName(pm.getMedicineEntity().getBrandName());
            bm.setUnitPrice(pm.getMedicineEntity().getPrice());
            bm.setQuantity(pm.getQuantity());
            bm.setTotalPrice(total);
            bm.setBillingEntity(bill);

            medicines.add(bm);
            medicineTotal = medicineTotal.add(total);
        }

        bill.setBillingMedicine(medicines);
        bill.setMedicineTotalPrice(medicineTotal);

        bill.setSubTotalAmount(
                bill.getConsultationFee().add(medicineTotal)
        );


        bill.setTaxAmount(BigDecimal.ZERO);
        bill.setInvoiceNumber(BillingNumberGenerate.generateBill());


        bill.setFinalPayableAmount(bill.getSubTotalAmount());
    }

    // ======================================================
    // HELPERS
    // ======================================================
    private UserEntity getUser(Authentication auth) {
        return userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UserNotFound("User not found"));
    }

    private void validateStaff(UserEntity user) {
        if (user.getRole() != GlobalRole.STAFF)
            throw new SomethingIsWrong("Unauthorized");

        if (!user.getIsActive())
            throw new SomethingIsWrong("Account disabled");

        DeskStaffProfile staff =
                staffRepository.findByUser(user)
                        .orElseThrow(() -> new ResourceNotAvailable("Staff profile not found"));

        if (!staff.isAdminApproved())
            throw new SomethingIsWrong("Staff not approved");

        if (!staff.isOnDuty())
            throw new SomethingIsWrong("Staff not on duty");
    }

    private AppointmentEntity findAppointment(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotAvailable("Appointment not found"));
    }

    private BillingEntity findBill(Long id) {
        return billingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotAvailable("Bill not found"));
    }
}
