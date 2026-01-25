package hospital_management_sytem.Service;

import hospital_management_sytem.Dto.AppointmentDto.*;
import hospital_management_sytem.Entity.AppointmentEntity;
import hospital_management_sytem.Entity.Profile.DoctorProfile;
import hospital_management_sytem.Entity.Profile.PatientProfile;
import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Enum.AppointmentStatus;
import hospital_management_sytem.Enum.GlobalRole;
import hospital_management_sytem.Exeption.ResourceAlreadyAvailable;
import hospital_management_sytem.Exeption.ResourceNotAvailable;
import hospital_management_sytem.Exeption.SomethingIsWrong;
import hospital_management_sytem.Mapping.AppointmentMapper;
import hospital_management_sytem.Repository.AppointmentRepository;
import hospital_management_sytem.Repository.ProfileRepository.DoctorProfileRepository;
import hospital_management_sytem.Repository.ProfileRepository.PatienProfileRepository;
import hospital_management_sytem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AppointmentService {

    private final DoctorProfileRepository doctorProfileRepository;
    private final PatienProfileRepository patienProfileRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(DoctorProfileRepository doctorProfileRepository, PatienProfileRepository patienProfileRepository, UserRepository userRepository, AppointmentRepository appointmentRepository) {
        this.doctorProfileRepository = doctorProfileRepository;
        this.patienProfileRepository = patienProfileRepository;
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    //user Will book appointment

    @Transactional
    public AppointmentResponseDto bookAppointment(Authentication authentication, AppointmentRequestDto dto){
        UserEntity user=findUserByAuthentication(authentication);

        PatientProfile profile=getPatientProfile(user);

        //date check
        LocalDate today=LocalDate.now();
        if (dto.getAppointmentDate().isBefore(today)){
           throw new SomethingIsWrong("Invalid date");
        }



        AppointmentEntity entity= AppointmentMapper.toEntity(dto);
            entity.setStatus(AppointmentStatus.REQUESTED);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setPatientProfile(profile);
        AppointmentEntity saved=appointmentRepository.save(entity);

        return AppointmentMapper.toResponseDto(saved);
    }

    @Transactional
    public AppointmentResponseDto confirmAppointmentAndAppointADoctor(Authentication authentication,AppointmentConfirmDto dto){
        UserEntity user=findUserByAuthentication(authentication);

        checkRole(user,GlobalRole.STAFF);

        if (dto.getDrId()<=0 || dto.getAppointmentId()<=0){
            throw new SomethingIsWrong("Wrong input");
        }

        AppointmentEntity entity=appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(()-> new ResourceNotAvailable("Appointment id don't exist!"));

        DoctorProfile doctorProfile=doctorProfileRepository.findById(dto.getDrId())
                .orElseThrow(()->new ResourceNotAvailable("Can't find Doctor profile"));

        if (entity.getStatus()!=AppointmentStatus.REQUESTED){
            throw new SomethingIsWrong("Can't assign the doctor the current status");
        }
        if (entity.getDoctorProfile()!=null){
            throw new ResourceAlreadyAvailable("Doctor already assigned");
        }
        if (!doctorProfile.getOnDuty()){
            throw new ResourceNotAvailable("Dr is not on duty");
        }
        if (!doctorProfile.getIsApprovedByAdmin()){
            throw new SomethingIsWrong("Dr is not verified by admin");
        }

        boolean conflict = appointmentRepository
                .existsDoctorScheduleConflict(
                        doctorProfile.getId(),
                        entity.getAppointmentDate(),
                        entity.getStartTime(),
                        entity.getEndTime()
                );

        if (conflict) {
            throw new SomethingIsWrong("Doctor already has an appointment at this time");
        }
        entity.setStatus(AppointmentStatus.ASSIGNED);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setDoctorProfile(doctorProfile);

      AppointmentEntity appointment=  appointmentRepository.save(entity);
      return AppointmentMapper.toResponseDto(appointment);
    }

    @Transactional
    public AppointmentResponseDto cancelAppointmentByPatient(Authentication authentication, AppointmentCancelDto dto){
        UserEntity user=findUserByAuthentication(authentication);


        checkRole(user,GlobalRole.PATIENT);

       AppointmentEntity appointmentEntity=findAppointment(dto.getAppointmentId());
        if (!user.getId().equals(appointmentEntity.getPatientProfile().getUser().getId())){
            throw new SomethingIsWrong("Not allowed to do this");
        }

        statusCheck(appointmentEntity,AppointmentStatus.ASSIGNED,AppointmentStatus.REQUESTED);

        appointmentEntity.setStatus(AppointmentStatus.CANCELLED_BY_PATIENT);

        if (dto.getCancelReason()!=null){
            appointmentEntity.setCancellationReason(dto.getCancelReason());
        }
        appointmentEntity.setUpdatedAt(LocalDateTime.now());

        AppointmentEntity appointment=appointmentRepository.save(appointmentEntity);

        return AppointmentMapper.toResponseDto(appointment);
   }

   @Transactional
   public AppointmentResponseDto cancelAppointmentByDoctor(Authentication authentication,AppointmentCancelDto dto){
        UserEntity user=findUserByAuthentication(authentication);

        checkRole(user,GlobalRole.DOCTOR);

        AppointmentEntity appointment=findAppointment(dto.getAppointmentId());

        statusCheck(appointment,AppointmentStatus.APPROVED,AppointmentStatus.ASSIGNED);
        if (!user.getId().equals(appointment.getDoctorProfile().getUser().getId())){
            throw new SomethingIsWrong("Not allowed!");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED_BY_DOCTOR);
        appointment.setUpdatedAt(LocalDateTime.now());
        appointment.setCancellationReason(dto.getCancelReason());

        AppointmentEntity appointmentEntity=appointmentRepository.save(appointment);

        return AppointmentMapper.toResponseDto(appointmentEntity);
   }

   @Transactional
   public AppointmentResponseDto cancelAppointmentByStaff(Authentication authentication,AppointmentCancelDto dto){
        UserEntity user=findUserByAuthentication(authentication);

        checkRole(user,GlobalRole.STAFF);

        AppointmentEntity appointment=findAppointment(dto.getAppointmentId());

       if (appointment.getStatus()!=AppointmentStatus.ASSIGNED && appointment.getStatus()!=AppointmentStatus.APPROVED && appointment.getStatus()!=AppointmentStatus.REQUESTED){
           throw new SomethingIsWrong("Can't cancel at this status");
       }
        appointment.setStatus(AppointmentStatus.CANCELLED_BY_STAFF);
        appointment.setUpdatedAt(LocalDateTime.now());
        appointment.setCancellationReason(dto.getCancelReason());

        AppointmentEntity appointmentEntity=appointmentRepository.save(appointment);

        return AppointmentMapper.toResponseDto(appointmentEntity);
   }

   @Transactional
   public AppointmentSucessResponseDto successfulAppointment(Authentication authentication, AppointmentSucessRequestDto dto){
        UserEntity user=findUserByAuthentication(authentication);

        checkRole(user,GlobalRole.DOCTOR);

        AppointmentEntity appointment=findAppointment(dto.getId());
        if (appointment.getStatus()!=AppointmentStatus.ASSIGNED){
            throw new SomethingIsWrong("Something wrong with status");
        }

        if (!user.getId().equals(appointment.getDoctorProfile().getUser().getId())){
            throw new SomethingIsWrong("Not Allowed!");
        }

        appointment.setUpdatedAt(LocalDateTime.now());
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment.setDoctorNote(dto.getDoctorNotes());

        appointmentRepository.save(appointment);

        AppointmentSucessResponseDto dto1=new AppointmentSucessResponseDto();
        dto1.setId(appointment.getId());
        dto1.setAppointmentDate(appointment.getAppointmentDate());
        dto1.setStartTime(appointment.getStartTime());
        dto1.setEndTime(appointment.getEndTime());
        dto1.setStatus(appointment.getStatus());
        dto1.setReason(appointment.getReason());
        dto1.setPatientName(appointment.getPatientProfile().getFullName());
        dto1.setDoctorName(appointment.getDoctorProfile().getFullName());
        dto1.setDoctorNote(appointment.getDoctorNote());


        return dto1;

   }




    /*-------------------------------------------------------------------------------------------------------
                                              HELPING METHOD
    -------------------------------------------------------------------------------------------------------- */

  public UserEntity findUserByAuthentication(Authentication authentication){
      return userRepository.findByUsername(authentication.getName())
              .orElseThrow(()->new ResourceNotAvailable("Can't find a Account"));
  }
  public PatientProfile getPatientProfile(UserEntity user){
      checkRole(user,GlobalRole.PATIENT);
      return patienProfileRepository.findByUser(user)
              .orElseThrow(()->new ResourceNotAvailable("Can't find a profile link to the account"));
  }

  public DoctorProfile getDoctorProfile(UserEntity user){
     checkRole(user,GlobalRole.DOCTOR);
      return doctorProfileRepository.findByUser(user)
              .orElseThrow(()->new ResourceNotAvailable("Can't find a profile link to the account"));
  }

  public void checkRole(UserEntity user,GlobalRole expected){
      if (user.getRole()!= expected){
          throw new SomethingIsWrong("Access denied");
      }
  }

  public AppointmentEntity findAppointment(Long id){
      AppointmentEntity appointmentEntity=appointmentRepository.findById(id)
              .orElseThrow(()-> new ResourceNotAvailable("Appointment not found with id"));

      return appointmentEntity;
  }

  public void statusCheck(AppointmentEntity appointment,AppointmentStatus status,AppointmentStatus status1){
      if (appointment.getStatus()!=status && appointment.getStatus()!=status1){
          throw new SomethingIsWrong("Can't cancel at this status");
      }
  }

}
