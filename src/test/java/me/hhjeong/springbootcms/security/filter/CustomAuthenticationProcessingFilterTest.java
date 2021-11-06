package me.hhjeong.springbootcms.security.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomAuthenticationProcessingFilterTest {

    @DisplayName("인증요청 메서드가 POST인지 확인")
    @Test
    void isPost() {
        CustomAuthenticationProcessingFilter filter = new CustomAuthenticationProcessingFilter("/login");

        assertThat(filter.isPost("POST")).isTrue();
        assertThat(filter.isPost("GET")).isFalse();
        assertThat(filter.isPost("PUT")).isFalse();
        assertThat(filter.isPost("DELETE")).isFalse();
    }
}