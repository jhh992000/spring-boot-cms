package me.hhjeong.springbootcms.account.domain;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountIdValidator implements ConstraintValidator<AccountId, String> {

    private static final Pattern patternFirstLetterAlphabet = Pattern.compile("^[a-zA-Z].*");
    private static final Pattern patternMixAlphabetAndNumber = Pattern.compile("^[a-zA-Z]+[_a-zA-Z0-9]*$");
    public static final String ID_INPUT_REQUIRE = "ID를 입력해주세요.";
    public static final String ID_MUST_FIRST_LETTER_IS_ALPHABET = "ID는 알파벳으로 시작해야 합니다.";
    public static final String ID_MUST_USE_ALPHABET_AND_NUMBER = "ID는 영문과 숫자만 입력가능합니다.";
    public static final String ID_INPUT_RANGE = "ID는 %d~%d 이내로 입력해주세요.";
    public static final int MIN = 5;
    public static final int MAX = 10;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext ctx) {
        if (isEmpty(id)) {
            addConstraintViolation(ctx, ID_INPUT_REQUIRE);
            return false;
        }
        if (!isFirstLetterAlphabet(id)) {
            addConstraintViolation(ctx, ID_MUST_FIRST_LETTER_IS_ALPHABET);
            return false;
        }
        if (!isMixAlphabetAndNumber(id)) {
            addConstraintViolation(ctx, ID_MUST_USE_ALPHABET_AND_NUMBER);
            return false;
        }
        if (!isMinMaxRange(id, MIN, MAX)) {
            addConstraintViolation(ctx, String.format(ID_INPUT_RANGE, MIN, MAX));
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
        if (isEmpty(id)) {
            return false;
        }
        return patternFirstLetterAlphabet.matcher(id).find();
    }

    public boolean isMixAlphabetAndNumber(String id) {
        if (isEmpty(id)) {
            return false;
        }
        return patternMixAlphabetAndNumber.matcher(id).find();
    }

    public boolean isMinMaxRange(String id, int min, int max) {
        if (isEmpty(id)) {
            return false;
        }
        return id.length() >= min && id.length() <= max;
    }
}
