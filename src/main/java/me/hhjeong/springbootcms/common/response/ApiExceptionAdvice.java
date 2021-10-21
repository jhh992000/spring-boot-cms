package me.hhjeong.springbootcms.common.response;

import java.nio.file.AccessDeniedException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionAdvice.class);

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final ApiException e) {
        logger.error(e.getMessage());
        pringStackTrace(e);

        return ResponseEntity
            .status(e.getError().getStatus())
            .body(ApiExceptionEntity.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(e.getError().getCode())
                .errorMessage(e.getError().getMessage())
                .build());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final RuntimeException e) {
        logger.error(e.getMessage());
        pringStackTrace(e);

        return ResponseEntity
            .status(ExceptionEnum.RUNTIME_EXCEPTION.getStatus())
            .body(ApiExceptionEntity.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(ExceptionEnum.RUNTIME_EXCEPTION.getCode())
                .errorMessage(e.getMessage())
                .build());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final AccessDeniedException e) {
        logger.error(e.getMessage());
        pringStackTrace(e);

        return ResponseEntity
            .status(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getStatus())
            .body(ApiExceptionEntity.builder()
                .status(HttpStatus.FORBIDDEN)
                .errorCode(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode())
                .errorMessage(e.getMessage())
                .build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final Exception e) {
        logger.error(e.getMessage());
        pringStackTrace(e);

        return ResponseEntity
            .status(ExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
            .body(ApiExceptionEntity.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                .errorMessage(e.getMessage())
                .build());
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiExceptionEntity> methodArgumentNotValidExceptionHandler(HttpServletRequest request, final MethodArgumentNotValidException e) {
        logger.error(e.getMessage());
        pringStackTrace(e);

        return ResponseEntity
            .status(ExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
            .body(ApiExceptionEntity.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                .errorMessage(e.getAllErrors().get(0).getDefaultMessage())
                .build());
    }

    private void pringStackTrace(Exception e) {
        if (logger.isDebugEnabled()) {
            e.printStackTrace();
        }
    }

}