package me.hhjeong.springbootcms.account.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountIdValidator implements ConstraintValidator<AccountId, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        //TODO - 사용자ID 유효성검증
        return false;
    }
}
