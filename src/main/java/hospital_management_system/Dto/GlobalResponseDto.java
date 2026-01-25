package hospital_management_sytem.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalResponseDto {
    private LocalDateTime localDateTime;
    private int status;
    private String error;
    private String message;
   private String path;
}
