package hospital_management_sytem.GlobalExeption;

import hospital_management_sytem.Dto.GlobalResponseDto;
import hospital_management_sytem.Exeption.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExeptiom {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<GlobalResponseDto> handleError(UserNotFound sc,HttpServletRequest request){
        return build(HttpStatus.NOT_FOUND,"Canl't find a Account", sc.getMessage(), request);
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<GlobalResponseDto> handleError1(UserAlreadyExist sc, HttpServletRequest request){
        return build(HttpStatus.ALREADY_REPORTED,"Info Already Regoster With Another Account", sc.getMessage(), request);
    }
    @ExceptionHandler(JwtExceptions.class)
    public ResponseEntity<GlobalResponseDto> handleError3(JwtExceptions sc, HttpServletRequest request){
        return build(HttpStatus.GONE,"Something wrong with token", sc.getMessage(), request);
    }

    @ExceptionHandler(InvalidDetails.class)
    public ResponseEntity<GlobalResponseDto> handleError4(InvalidDetails sc, HttpServletRequest request){
        return build(HttpStatus.BAD_REQUEST,"Wrong info", sc.getMessage(), request);
    }

    @ExceptionHandler(SomethingIsWrong.class)
    public ResponseEntity<GlobalResponseDto> handleError4(SomethingIsWrong sc, HttpServletRequest request){
        return build(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong", sc.getMessage(), request);
    }

    @ExceptionHandler(OtpRelatedExeption.class)
    public ResponseEntity<GlobalResponseDto> handleError4(OtpRelatedExeption sc, HttpServletRequest request){
        return build(HttpStatus.BAD_REQUEST,"Otp related error", sc.getMessage(), request);
    }

    @ExceptionHandler(ResourceNotAvailable.class)
    public ResponseEntity<GlobalResponseDto> handleError4(ResourceNotAvailable sc, HttpServletRequest request){
        return build(HttpStatus.NOT_FOUND,"Can't find resource", sc.getMessage(), request);
    }

    @ExceptionHandler(ResourceAlreadyAvailable.class)
    public ResponseEntity<GlobalResponseDto> handleError4(ResourceAlreadyAvailable sc, HttpServletRequest request){
        return build(HttpStatus.ALREADY_REPORTED,"Resource Already available", sc.getMessage(), request);
    }
     @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponseDto> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return build(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                message,
                request
        );
    }


    public ResponseEntity<GlobalResponseDto> build(
           HttpStatus status,
           String error,
           String message,
           HttpServletRequest request
    ){
        GlobalResponseDto responseDto=new GlobalResponseDto(
                LocalDateTime.now(),
                status.value(),
                error,
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(responseDto);
    }
}

