package hospital_management_sytem.Repository;

import hospital_management_sytem.Dto.AppointmentDto.AppointmentRequestDto;
import hospital_management_sytem.Entity.AppointmentEntity;
import hospital_management_sytem.Entity.Profile.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long> {
    Optional<AppointmentEntity>
    findByPatientProfileAndAppointmentDate(
            PatientProfile patientProfile,
            LocalDate appointmentDate
    );



    @Query("""
    SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
    FROM AppointmentEntity a
    WHERE a.doctorProfile.id = :doctorId
      AND a.appointmentDate = :date
      AND (
            (:startTime < a.endTime AND :endTime > a.startTime)
          )
""")
    boolean existsDoctorScheduleConflict(
            @Param("doctorId") Long doctorId,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );


    Optional<AppointmentEntity> findByDoctorProfile_User_Username(String username);
}
