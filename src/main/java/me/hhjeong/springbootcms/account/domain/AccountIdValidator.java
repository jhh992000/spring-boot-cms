package me.hhjeong.springbootcms.account.domain;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountIdValidator implements ConstraintValidator<AccountId, String> {

    private static final Pattern patternFirstLetterAlphabet = Pattern.compile("^[a-zA-Z].*");
    private static final Pattern patternMixAlphabetAndNumber = Pattern.compile("^[a-zA-Z]+[_a-zA-Z0-9]*$");
    public static final String PLEASE_ENTER_YOUR_ID = "ID를 입력해주세요.";
    public static final String ID_MUST_START_WITH_AN_ALPHABET = "ID는 알파벳으로 시작해야 합니다.";
    public static final String ID_MUST_USE_ALPHABET_AND_NUMBER = "ID는 영문과 숫자만 입력가능합니다.";
    public static final String ID_INPUT_RANGE = "ID는 %d~%d 이내로 입력해주세요.";
    public static final int ID_MIN = 5;
    public static final int ID_MAX = 10;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext ctx) {
        if (isEmpty(id)) {
            addConstraintViolation(ctx, PLEASE_ENTER_YOUR_ID);
            return false;
        }
        if (!isFirstLetterAlphabet(id)) {
            addConstraintViolation(ctx, ID_MUST_START_WITH_AN_ALPHABET);
            return false;
        }
        if (!isMixAlphabetAndNumber(id)) {
            addConstraintViolation(ctx, ID_MUST_USE_ALPHABET_AND_NUMBER);
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

    public boolean isFirstLetterAlphabet(String id) {
        return patternFirstLetterAlphabet.matcher(id).find();
    }

    public boolean isMixAlphabetAndNumber(String id) {
        return patternMixAlphabetAndNumber.matcher(id).find();
    }

    public boolean withinRange(int min, int max, String id) {
        return id.length() >= min && id.length() <= max;
    }
}
