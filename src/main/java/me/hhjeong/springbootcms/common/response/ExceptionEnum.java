package me.hhjeong.springbootcms.common.response;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001", "잘못된 요청입니다."),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002", "인증정보가 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "E0003", "접근 권한이 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0004"),

    SECURITY_01(HttpStatus.FORBIDDEN, "S0001", "접근 권한이 없습니다."),
    SECURITY_02(HttpStatus.UNAUTHORIZED, "S0002", "토큰이 만료되었습니다.");

    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
