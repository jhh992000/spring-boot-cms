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

@DisplayName("사용자 ID 유효성 검사")
@ExtendWith(MockitoExtension.class) //junit5부터 mokito 사용시 필요한 annotation
public class AccountIdValidatorTest {

    private final AccountIdValidator validator = new AccountIdValidator();

    @Mock
    private ConstraintValidatorContext ctx;
    @Mock
    private ConstraintViolationBuilder builder;

    @Test
    void ID입력값이_유효함() {
        assertThat(validator.isValid("abcde", ctx)).isTrue();
        assertThat(validator.isValid("abcde", ctx)).isTrue();
    }

    @Test
    void ID입력값이_유효하지_않음() {
        willDoNothing().given(ctx).disableDefaultConstraintViolation();
        given(ctx.buildConstraintViolationWithTemplate(any())).willReturn(builder);
        given(builder.addConstraintViolation()).willReturn(ctx);

        assertThat(validator.isValid("1abcd", ctx)).isFalse();
        assertThat(validator.isValid("abcdef123!", ctx)).isFalse();
    }

    @Test
    void ID가_빈값인_경우_오류발생() {
        assertThat(validator.isEmpty(null)).isTrue();
        assertThat(validator.isEmpty("")).isTrue();
    }

    @Test
    void ID는_영문으로_시작() {
        assertThat(validator.isFirstLetterAlphabet("abc")).isTrue();
        assertThat(validator.isFirstLetterAlphabet("123")).isFalse();
        assertThat(validator.isFirstLetterAlphabet("!@#")).isFalse();
    }

    @Test
    void ID가_영문과_숫자로만_이루어져_있는지_체크() {
        assertThat(validator.isMixAlphabetAndNumber("abc")).isTrue();
        assertThat(validator.isMixAlphabetAndNumber("abc123")).isTrue();
        assertThat(validator.isMixAlphabetAndNumber("abcd!")).isFalse();
        assertThat(validator.isMixAlphabetAndNumber("abcd#ef$")).isFalse();
        assertThat(validator.isMixAlphabetAndNumber("테스트아이디")).isFalse();
    }

    @Test
    void ID의_길이가_범위_이내인지_체크() {
        assertThat(validator.withinRange(5, 10, "abcd")).isFalse();
        assertThat(validator.withinRange(5, 10, "abcde")).isTrue();
        assertThat(validator.withinRange(5, 10, "abcdeefghij")).isFalse();
    }

}
