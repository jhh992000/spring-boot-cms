package me.hhjeong.springbootcms.common.config;

import me.hhjeong.springbootcms.common.security.filter.CustomAuthenticationProcessingFilter;
import me.hhjeong.springbootcms.common.security.handler.CustomAuthenticationSuccessHandler;
import me.hhjeong.springbootcms.common.security.jwt.JwtSecurityConfig;
import me.hhjeong.springbootcms.common.security.jwt.TokenProvider;
import me.hhjeong.springbootcms.common.security.provider.CustomAuthenticationProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String DEFAULT_FILTER_PROCESSES_URL = "/login";
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationProvider provider;
    private final TokenProvider tokenProvider;

    public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler, CustomAuthenticationProvider provider,
        TokenProvider tokenProvider) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.provider = provider;
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .authenticationProvider(this.provider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")

            // allow anonymous resource requests
            .antMatchers(
                "/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/h2-console/**"
            );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            // token을 사용하는 방식이기 때문에 csrf를 disable
            .csrf().disable()

            // 세션을 사용하지 않기 때문에 STATELESS로 설정
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()

            //폼로그인 비활성화
            .formLogin().disable()

            .headers().frameOptions().disable()

            .and()
            .authorizeRequests()
            .antMatchers("/api/account").hasAuthority("ROLE_USER")
            .antMatchers("/api/account/user-feature").hasAuthority("ROLE_USER")
            .antMatchers("/api/account/admin-feature").hasAuthority("ROLE_ADMIN")
            .anyRequest().authenticated()
            .and()

            //커스텀 인증필터 추가
            .addFilterBefore(customAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .apply(jwtSecurityConfig());
    }

    protected CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter() throws Exception {
        CustomAuthenticationProcessingFilter filter = new CustomAuthenticationProcessingFilter(DEFAULT_FILTER_PROCESSES_URL, customAuthenticationSuccessHandler, null);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    //TODO - 별도로 설정을 왜 빼는것인지? 바로 addFilterBefore 해도 될듯?
    private JwtSecurityConfig jwtSecurityConfig() {
        return new JwtSecurityConfig(tokenProvider);
    }

}
