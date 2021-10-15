package me.hhjeong.springbootcms.account.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("사용자 패스워드 유효성 검사기")
@ExtendWith(MockitoExtension.class)
public class AccountPasswordValidatorTest {

    private final AccountPasswordValidator validator = new AccountPasswordValidator();

    @Mock
    private ConstraintValidatorContext ctx;
    @Mock
    private ConstraintViolationBuilder builder;

    @Test
    void 비밀번호_입력값이_유효함() {
        assertThat(validator.isValid("Password1!", ctx)).isTrue();
    }

    @Test
    void 비밀번호_입력값이_유효하지_않음() {
        willDoNothing().given(ctx).disableDefaultConstraintViolation();
        given(ctx.buildConstraintViolationWithTemplate(any())).willReturn(builder);
        given(builder.addConstraintViolation()).willReturn(ctx);

        assertThat(validator.isValid("password", ctx)).isFalse();
        assertThat(validator.isValid("PASSWORD", ctx)).isFalse();
        assertThat(validator.isValid("password1", ctx)).isFalse();
        assertThat(validator.isValid("PASSWORD1", ctx)).isFalse();
        assertThat(validator.isValid("password1@", ctx)).isFalse();
        assertThat(validator.isValid("password@", ctx)).isFalse();
        assertThat(validator.isValid("Password@", ctx)).isFalse();
    }

    @Test
    void 비밀번호가_빈값인경우_체크() {
        assertThat(validator.isEmpty(null)).isTrue();
        assertThat(validator.isEmpty("")).isTrue();
    }

    @Test
    void 비밀번호에_영소문자가_포함되어있는지_체크() {
        assertThat(validator.hasLowerCaseLetters("abcdefg")).isTrue();
        assertThat(validator.hasLowerCaseLetters("ABCDEFG")).isFalse();
        assertThat(validator.hasLowerCaseLetters("abcDEFG")).isTrue();
        assertThat(validator.hasLowerCaseLetters("!@#$%^&*()")).isFalse();
    }

    @Test
    void 비밀번호에_영대문자가_포함되어있는지_체크() {
        assertThat(validator.hasUpperCaseLetters("ABCDEFG")).isTrue();
        assertThat(validator.hasUpperCaseLetters("abcdefg")).isFalse();
        assertThat(validator.hasUpperCaseLetters("abcDEFG")).isTrue();
        assertThat(validator.hasUpperCaseLetters("!@#$%^&*()")).isFalse();
    }

    @Test
    void 비밀번호에_숫자가_포함되어있는지_체크() {
        assertThat(validator.hasNumerics("12345")).isTrue();
        assertThat(validator.hasNumerics("abc123")).isTrue();
        assertThat(validator.hasNumerics("abcdefg")).isFalse();
        assertThat(validator.hasNumerics("!@#$%^&*()")).isFalse();
    }

    @Test
    void 비밀번호에_특수문자가_포함되어있는지_체크() {
        assertThat(validator.hasSpecialCharacters("!@#$%^&*()")).isTrue();
        assertThat(validator.hasSpecialCharacters("abcd!@#$")).isTrue();
        assertThat(validator.hasSpecialCharacters("abc123")).isFalse();
        assertThat(validator.hasSpecialCharacters("abcdefg")).isFalse();
    }

    @Test
    void 비밀번호의_길이가_범위_이내인지_체크() {
        assertThat(validator.withinRange(8, 10, "abcdefgh")).isTrue();
        assertThat(validator.withinRange(8, 10, "abcdefg")).isFalse();
        assertThat(validator.withinRange(8, 10, "abcdefghijk")).isFalse();
    }

}
