package hospital_management_sytem.Service;

import hospital_management_sytem.Dto.MedicineDto.MedicineEditDto;
import hospital_management_sytem.Dto.MedicineDto.MedicineRequestDto;
import hospital_management_sytem.Dto.MedicineDto.MedicineResponseDto;
import hospital_management_sytem.Entity.MedicineEntity;
import hospital_management_sytem.Entity.Profile.DeskStaffProfile;
import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Enum.GlobalRole;
import hospital_management_sytem.Exeption.ResourceNotAvailable;
import hospital_management_sytem.Exeption.SomethingIsWrong;
import hospital_management_sytem.Exeption.UserNotFound;
import hospital_management_sytem.Mapping.MedicineMapper;
import hospital_management_sytem.Repository.MedicineRepository;
import hospital_management_sytem.Repository.ProfileRepository.StaffRepository;
import hospital_management_sytem.Repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;

    public MedicineService(MedicineRepository medicineRepository, UserRepository userRepository, StaffRepository staffRepository) {
        this.medicineRepository = medicineRepository;
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
    }


    public MedicineResponseDto createMedicine(Authentication authentication, MedicineRequestDto dto){
        validateStaffAccess(authentication);

        MedicineEntity entity= MedicineMapper.toEntity(dto);
        MedicineEntity saved=medicineRepository.save(entity);

        return MedicineMapper.toResponseDto(saved);
    }

    public MedicineResponseDto editMedicine(Authentication authentication,String medicineCode, MedicineEditDto dto){
        validateStaffAccess(authentication);

       MedicineEntity medicineEntity=findMedicine(medicineCode);

     if (dto.getName()!=null){
         medicineEntity.setName(dto.getName());
     }
     if (dto.getBrandName()!=null){
         medicineEntity.setBrandName(dto.getBrandName());
     }
     if (dto.getCategory()!=null){
         medicineEntity.setCategory(dto.getCategory());
     }
     if (dto.getStrength()!=null){
         medicineEntity.setStrength(dto.getStrength());
     }
     if (dto.getPrice()!=null){
         medicineEntity.setPrice(dto.getPrice());
     }
     if (dto.getExpiryDate()!=null){
         medicineEntity.setExpiryDate(dto.getExpiryDate());
     }
     if (dto.getStockQuantity()!=null){
         medicineEntity.setStockQuantity(dto.getStockQuantity());
     }

     MedicineEntity saved=medicineRepository.save(medicineEntity);

     return MedicineMapper.toResponseDto(saved);

    }

    public void delete(Authentication authentication,String medicineCode){
        validateStaffAccess(authentication);

        MedicineEntity medicineEntity=findMedicine(medicineCode);
        medicineEntity.setIsActive(false);

        medicineRepository.save(medicineEntity);
    }


    //anyone can see it doctor/patient/staff
    public MedicineResponseDto getMedicine(String medicineCode){
        MedicineEntity medicineEntity=findMedicine(medicineCode);

        if (!medicineEntity.getIsActive()){
            throw new SomethingIsWrong("Medicine has been removed");
        }

        return MedicineMapper.toResponseDto(medicineEntity);
    }

    public List<MedicineResponseDto> getAllMedicine(){
       List <MedicineEntity> medicineEntityList=medicineRepository.findAllByIsActiveTrue();

       return medicineEntityList.stream().map(MedicineMapper::toResponseDto).collect(Collectors.toList());
    }

 /*--------------------------------------------------------------------------------------------------------
                                            HELPER METHODS
 ----------------------------------------------------------------------------------------------------------*/

 public UserEntity findByUserAuthentication(Authentication authentication){
     return userRepository.findByUsername(authentication.getName())
             .orElseThrow(()-> new UserNotFound("Can't find User"));
 }
 public void roleCheck(UserEntity user, GlobalRole role){
     if (!user.getRole().equals(role)){
         throw new SomethingIsWrong("Not Authorized!");
     }
 }

 public MedicineEntity findMedicine(String medicineCode){
     MedicineEntity medicineEntity=medicineRepository.findByMedicineCode(medicineCode)
             .orElseThrow(()-> new ResourceNotAvailable("Can't find medicine"));
     return medicineEntity;

 }
 public void validateStaffAccess(Authentication authentication){
    UserEntity user=  findByUserAuthentication(authentication);
    roleCheck(user,GlobalRole.STAFF);

     DeskStaffProfile profile=staffRepository.findByUser(user)
             .orElseThrow(()->new ResourceNotAvailable("Profile Does not exist"));

     if (!profile.isAdminApproved()){
         throw new SomethingIsWrong("Not approved By Admin to work");
     }
     if (!profile.isOnDuty()){
         throw new SomethingIsWrong("Can't do this activity now");
     }
 }
}
