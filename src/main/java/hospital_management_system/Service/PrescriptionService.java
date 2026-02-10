package hospital_management_sytem.Service;

import hospital_management_sytem.Dto.PrescriptionDto.*;
import hospital_management_sytem.Entity.*;
import hospital_management_sytem.Entity.Prescription.PrescriptionEntity;
import hospital_management_sytem.Entity.Prescription.PrescriptionMedicineEntity;
import hospital_management_sytem.Entity.Profile.DoctorProfile;
import hospital_management_sytem.Enum.GlobalRole;
import hospital_management_sytem.Exeption.ResourceAlreadyAvailable;
import hospital_management_sytem.Exeption.ResourceNotAvailable;
import hospital_management_sytem.Exeption.SomethingIsWrong;
import hospital_management_sytem.Exeption.UserNotFound;
import hospital_management_sytem.Mapping.PrescriptionMapping.PrescriptionMapper;
import hospital_management_sytem.Repository.AppointmentRepository;
import hospital_management_sytem.Repository.MedicineRepository;
import hospital_management_sytem.Repository.Prescription.PrescriptiomMedicineRepository;
import hospital_management_sytem.Repository.Prescription.PrescriptionRepository;
import hospital_management_sytem.Repository.ProfileRepository.DoctorProfileRepository;
import hospital_management_sytem.Repository.ProfileRepository.PatienProfileRepository;
import hospital_management_sytem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final UserRepository userRepository;
    private final MedicineRepository medicineRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final AppointmentRepository appointmentRepository;
    private final PrescriptiomMedicineRepository prescriptiomMedicineRepository;
    public PrescriptionService(PrescriptionRepository prescriptionRepository, UserRepository userRepository, MedicineRepository medicineRepository, DoctorProfileRepository doctorProfileRepository, AppointmentRepository appointmentRepository, PrescriptiomMedicineRepository prescriptiomMedicineRepository ) {
        this.prescriptionRepository = prescriptionRepository;
        this.userRepository = userRepository;
        this.medicineRepository = medicineRepository;
        this.doctorProfileRepository = doctorProfileRepository;
        this.appointmentRepository = appointmentRepository;
        this.prescriptiomMedicineRepository = prescriptiomMedicineRepository;
    }

    @Transactional
    public PrescriptionResponseDto createPrescription(
            Authentication authentication,
            PrescriptionRequestDto dto
    ) {
        confirmDoctor(authentication);

        AppointmentEntity appointment = findAppointment(dto.getAppointmentId());

        if (prescriptionRepository.findByAppointmentEntity_Id(dto.getAppointmentId()).isPresent()) {
            throw new ResourceAlreadyAvailable("Prescription already exists");
        }

        PrescriptionEntity prescription = new PrescriptionEntity();
        prescription.setAppointmentEntity(appointment);

        createPrescriptionMedicines(dto, prescription);

        prescription.setIsComplete(false);

        PrescriptionEntity saved = prescriptionRepository.save(prescription);

        return PrescriptionMapper.toDto(saved);
    }
    public void invalid(Authentication authentication, DeletePrescription deletePrescription){
        UserEntity user=findUserByAuthentication(authentication);

        DoctorProfile doctorProfile=findDoctorProfile(user);

        PrescriptionEntity prescriptionEntity=findPrescription(deletePrescription.getPrescriptionId());

        if (!prescriptionEntity.getValid()){
            throw new SomethingIsWrong("Prescription is already invalid");
        }

        prescriptionEntity.setValid(false);

        prescriptionRepository.save(prescriptionEntity);
    }

    public void markPrescriptionComplete(Authentication authentication, DeletePrescription deletePrescription){
        UserEntity user=findUserByAuthentication(authentication);

        DoctorProfile doctorProfile=findDoctorProfile(user);

        PrescriptionEntity prescriptionEntity=findPrescription(deletePrescription.getPrescriptionId());
        AppointmentEntity appointment=prescriptionEntity.getAppointmentEntity();

        if (!isUserDoctor(user,appointment) && !isUserPatient(user,appointment)){
            throw new SomethingIsWrong("Not Authorized!");
        }

        if (prescriptionEntity.getIsComplete()){
            throw new SomethingIsWrong("Prescription is already completed");
        }

        prescriptionEntity.setIsComplete(true);

        prescriptionRepository.save(prescriptionEntity);
    }


    public PrescriptionResponseDto getPrescription(Authentication authentication,Long prescriptionId){
        UserEntity user=findUserByAuthentication(authentication);

        PrescriptionEntity prescriptionEntity=findPrescription(prescriptionId);
        AppointmentEntity appointment=prescriptionEntity.getAppointmentEntity();

        if (!isUserDoctor(user,appointment) && !isUserPatient(user,appointment)){
            throw new SomethingIsWrong("Not Authorized!");
        }

        if (user.getRole().equals(GlobalRole.ADMIN)&& user.getRole().equals(GlobalRole.STAFF)){
            throw new SomethingIsWrong("Not Authorized!");
        }

        return PrescriptionMapper.toDto(prescriptionEntity);
    }




    /*-------------------------------------------------------------------------------------------------------------------------
                                                  HELPER METHOD
    --------------------------------------------------------------------------------------------------------------------------*/

    public void createPrescriptionMedicines(PrescriptionRequestDto dto,PrescriptionEntity prescriptionEntity){

        List<PrescriptionMedicineEntity> medicineEntities= new ArrayList<>();

        for (PrescriptionMedicineRequestDto midDto: dto.getMedicines()){
            MedicineEntity medicineEntity=medicineRepository.findByMedicineCode(midDto.getMedicineCode())
                    .orElseThrow(()-> new ResourceNotAvailable("Medicine does not exist by "+ midDto.getMedicineCode()));

            if(!medicineEntity.getIsActive()){
                throw new SomethingIsWrong("Medicine is not available anymore");
            }
            if (medicineEntity.getStockQuantity()<=0){
                throw new ResourceNotAvailable("Medicine isn't't in stock");
            }
            PrescriptionMedicineEntity pm=new PrescriptionMedicineEntity();
            pm.setMedicineEntity(medicineEntity);

            pm.setFrequency(midDto.getFrequency());
            pm.setDosage(midDto.getDosage());
            pm.setQuantity(midDto.getQuantity());
            pm.setDuration(midDto.getDuration());

            pm.setPrescriptionEntity(prescriptionEntity);

            medicineEntities.add(pm);
        }

        prescriptionEntity.setPrescriptionMedicineEntity(medicineEntities);

    }
    public UserEntity findUserByAuthentication(Authentication authentication){
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UserNotFound("Can't find User"));
    }

    public MedicineEntity findMedicine(String medicineCode){
        return medicineRepository.findByMedicineCode(medicineCode)
                .orElseThrow(()-> new ResourceNotAvailable("Can't find medicine"));
    }

    public void roleCheck(UserEntity user, GlobalRole role){
        if (!user.getRole().equals(role)){
            throw new SomethingIsWrong("Not Authorized!");
        }
    }

    public void confirmDoctor(Authentication authentication){
        UserEntity userEntity=findUserByAuthentication(authentication);
        roleCheck(userEntity,GlobalRole.DOCTOR);

        DoctorProfile profile=doctorProfileRepository.findByUser(userEntity)
                .orElseThrow(()-> new ResourceNotAvailable("Profile Not Available!"));

        if (!profile.getOnDuty()){
            throw new SomethingIsWrong("Profile is offline");
        }

        if (!profile.getIsActive()){
            throw new SomethingIsWrong("Account is not active!");
        }
        if (!profile.getIsApprovedByAdmin()){
            throw new SomethingIsWrong("Profile not approved by Admin!");
        }
    }

    public AppointmentEntity findAppointment(Long id){
        return appointmentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotAvailable("Can't find appointment!"));
    }
    public PrescriptionEntity findPrescription(Long id){
        return prescriptionRepository.findById(id)
                .orElseThrow(()-> new ResourceNotAvailable("Can't find appointment!"));
    }

    public void findIfItsSameDoctor(Authentication authentication,Long id){
        UserEntity userEntity=findUserByAuthentication(authentication);;

        DoctorProfile profile=findDoctorProfile(userEntity);


        AppointmentEntity appointment=findAppointment(id);

        if(!appointment.getDoctorProfile().getUser().getId().equals(profile.getUser().getId())){
            throw new SomethingIsWrong("Not Authorized");
        }

    }

    public Boolean isUserDoctor(UserEntity user,AppointmentEntity appointment){
        return appointment.getDoctorProfile().getUser().getId().equals(user.getId());
    }

    public Boolean isUserPatient(UserEntity user,AppointmentEntity appointment){
        return appointment.getPatientProfile().getUser().getId().equals(user.getId());
    }

    public DoctorProfile findDoctorProfile(UserEntity user){
        return doctorProfileRepository.findByUser(user)
                .orElseThrow(()->new ResourceNotAvailable("Profile can't find"));
    }
}
