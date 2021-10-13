package me.hhjeong.springbootcms.account.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("사용자 ID 유효성 검사")
public class AccountIdValidatorTest {

    private final AccountIdValidator validator = new AccountIdValidator();

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
    void ID가_5바이트_이상_15바이트_이내인지_체크() {
        assertThat(validator.isMinMaxRange("abcd", 5, 10)).isFalse();
        assertThat(validator.isMinMaxRange("abcde", 5, 10)).isTrue();
        assertThat(validator.isMinMaxRange("abcdeefghij", 5, 10)).isFalse();
    }

}
