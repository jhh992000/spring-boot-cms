package me.hhjeong.springbootcms.account.domain;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountPasswordValidator implements ConstraintValidator<AccountPassword, String> {

    private static final Pattern PATTERN_UPPER_ALPHABET = Pattern.compile(".*[A-Z].*");
    private static final Pattern PATTERN_LOWER_ALPHABET = Pattern.compile(".*[a-z].*");
    private static final Pattern PATTERN_NUMERIC = Pattern.compile(".*\\d.*");
    private static final Pattern patternSpecialCharacters = Pattern.compile(".*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*");
    public static final String PLEASE_ENTER_YOUR_PASSWORD = "비밀번호를 입력해주세요.";
    public static final String PASSWORD_MUST_INCLUDE_LOWERCASE_LETTERS = "비밀번호는 영소문자를 포함해야 합니다.";
    public static final String PASSWORD_MUST_INCLUDE_UPPERCASE_LETTERS = "비밀번호는 영대문자를 포함해야 합니다.";
    public static final String PASSWORD_MUST_INCLUDE_NUMERICS = "비밀번호는 숫자를 포함해야 합니다.";
    public static final String PASSWORD_MUST_INCLUDE_SPECIAL_CHARACTERS = "비밀번호는 특수문자를 포함해야 합니다.";
    public static final String PASSWORD_INPUT_RANGE = "비밀번호는 %d~%d 이내로 입력해주세요.";
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 20;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext ctx) {
        if (isEmpty(password)) {
            addConstraintViolation(ctx, PLEASE_ENTER_YOUR_PASSWORD);
            return false;
        }
        if (!hasLowerCaseLetters(password)) {
            addConstraintViolation(ctx, PASSWORD_MUST_INCLUDE_LOWERCASE_LETTERS);
            return false;
        }
        if (!hasUpperCaseLetters(password)) {
            addConstraintViolation(ctx, PASSWORD_MUST_INCLUDE_UPPERCASE_LETTERS);
            return false;
        }
        if (!hasNumerics(password)) {
            addConstraintViolation(ctx, PASSWORD_MUST_INCLUDE_NUMERICS);
            return false;
        }
        if (!hasSpecialCharacters(password)) {
            addConstraintViolation(ctx, PASSWORD_MUST_INCLUDE_SPECIAL_CHARACTERS);
            return false;
        }
        if (!withinRange(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH, password)) {
            addConstraintViolation(ctx, String.format(PASSWORD_INPUT_RANGE, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH));
            return false;
        }
        return true;
    }

    protected void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    public boolean isEmpty(String password) {
        return password == null || password.isEmpty();
    }

    public boolean hasLowerCaseLetters(String password) {
        return PATTERN_LOWER_ALPHABET.matcher(password).find();
    }

    public boolean hasUpperCaseLetters(String password) {
        return PATTERN_UPPER_ALPHABET.matcher(password).find();
    }

    public boolean hasNumerics(String password) {
        return PATTERN_NUMERIC.matcher(password).find();
    }

    public boolean hasSpecialCharacters(String password) {
        return patternSpecialCharacters.matcher(password).find();
    }

    public boolean withinRange(int min, int max, String password) {
        return password.length() >= min && password.length() <= max;
    }
}
