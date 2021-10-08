package me.hhjeong.springbootcms.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiExceptionEntity {

    private final int status;
    private final String errorCode;
    private final String errorMessage;

    @Builder
    public ApiExceptionEntity(HttpStatus status, String errorCode, String errorMessage) {
        this.status = status.value();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
