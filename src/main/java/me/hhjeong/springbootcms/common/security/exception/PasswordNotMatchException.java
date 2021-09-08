package me.hhjeong.springbootcms.common.security.exception;

public class PasswordNotMatchException extends RuntimeException {

    public PasswordNotMatchException() {
        super("비밀번호가 틀렸습니다.");
    }
}
