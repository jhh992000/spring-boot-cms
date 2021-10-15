package me.hhjeong.springbootcms.account.domain;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountIdValidator implements ConstraintValidator<AccountId, String> {

    private static final Pattern PATTERN_EMAIL = Pattern.compile("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$");
    public static final String PLEASE_ENTER_YOUR_ID = "ID를 입력해주세요.";
    public static final String ID_MUST_EMAIL = "ID는 이메일형식으로 입력하셔야 합니다.";
    public static final String ID_INPUT_RANGE = "ID는 %d~%d 이내로 입력해주세요.";
    public static final int ID_MIN = 3;
    public static final int ID_MAX = 320;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext ctx) {
        if (isEmpty(id)) {
            addConstraintViolation(ctx, PLEASE_ENTER_YOUR_ID);
            return false;
        }
        if (!isEmailPattern(id)) {
            addConstraintViolation(ctx, ID_MUST_EMAIL);
            return false;
        }
        if (!withinRange(ID_MIN, ID_MAX, id)) {
            addConstraintViolation(ctx, String.format(ID_INPUT_RANGE, ID_MIN, ID_MAX));
            return false;
        }
        return true;
    }

    protected void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    public boolean isEmpty(String id) {
        return id == null || id.isEmpty();
    }

    public boolean isEmailPattern(String id) {
        return PATTERN_EMAIL.matcher(id).find();
    }

    public boolean withinRange(int min, int max, String id) {
        return id.length() >= min && id.length() <= max;
    }
}
