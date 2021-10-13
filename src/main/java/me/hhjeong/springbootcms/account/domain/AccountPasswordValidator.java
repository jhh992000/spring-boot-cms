package me.hhjeong.springbootcms.account.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountPasswordValidator implements ConstraintValidator<AccountPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        //TODO - 비밀번호 유효성 검증
        return false;
    }

}
