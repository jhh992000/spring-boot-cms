package me.hhjeong.springbootcms.common.config;

import java.util.Arrays;
import java.util.List;
import me.hhjeong.springbootcms.common.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import me.hhjeong.springbootcms.common.security.factory.UrlResourcesMapFactoryBean;
import me.hhjeong.springbootcms.common.security.filter.CustomAuthenticationProcessingFilter;
import me.hhjeong.springbootcms.common.security.handler.CustomAuthenticationSuccessHandler;
import me.hhjeong.springbootcms.common.security.jwt.JwtFilter;
import me.hhjeong.springbootcms.common.security.jwt.TokenProvider;
import me.hhjeong.springbootcms.common.security.provider.CustomAuthenticationProvider;
import me.hhjeong.springbootcms.common.security.service.SecurityResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String DEFAULT_FILTER_PROCESSES_URL = "/login";
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationProvider provider;
    private final TokenProvider tokenProvider;
    private final SecurityResourceService securityResourceService;

    public SecurityConfig(
        CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
        CustomAuthenticationProvider provider,
        TokenProvider tokenProvider,
        SecurityResourceService securityResourceService
    ) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.provider = provider;
        this.tokenProvider = tokenProvider;
        this.securityResourceService = securityResourceService;
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
            /*
            .and()
            .authorizeRequests()
            .antMatchers("/api/account").hasAuthority("ROLE_USER")
            .antMatchers("/api/account/user-feature").hasAuthority("ROLE_USER")
            .antMatchers("/api/account/admin-feature").hasAuthority("ROLE_ADMIN")
            .anyRequest().authenticated()
            */
            .and()

            .addFilterBefore(customAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
    }

    protected CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter() throws Exception {
        CustomAuthenticationProcessingFilter filter = new CustomAuthenticationProcessingFilter(DEFAULT_FILTER_PROCESSES_URL, customAuthenticationSuccessHandler, null);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    protected JwtFilter jwtFilter() {
        return new JwtFilter(tokenProvider);
    }

    @Bean
    public FilterSecurityInterceptor customFilterSecurityInterceptor() throws Exception {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());   // URL방식의 권한정보 설정
        filterSecurityInterceptor.setAccessDecisionManager(new AffirmativeBased(Arrays.asList(new RoleVoter()))); // AccessDecisionManager 설정
        filterSecurityInterceptor.setAuthenticationManager(super.authenticationManagerBean());
        return filterSecurityInterceptor;
    }

    @Bean
    public FilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() {
        return new UrlFilterInvocationSecurityMetadataSource(new UrlResourcesMapFactoryBean(securityResourceService).getObject());
    }

}
