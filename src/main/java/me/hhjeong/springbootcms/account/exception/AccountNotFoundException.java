package me.hhjeong.springbootcms.account.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        super("계정을 찾을 수 없습니다.");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
