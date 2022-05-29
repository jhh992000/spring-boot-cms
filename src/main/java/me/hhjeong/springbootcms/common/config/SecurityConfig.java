package me.hhjeong.springbootcms.common.config;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import me.hhjeong.springbootcms.security.factory.UrlResourcesMapFactoryBean;
import me.hhjeong.springbootcms.security.filter.CustomAuthenticationProcessingFilter;
import me.hhjeong.springbootcms.security.filter.PermitAllFilter;
import me.hhjeong.springbootcms.security.handler.CustomAccessDeniedHandler;
import me.hhjeong.springbootcms.security.handler.CustomAuthenticationEntryPoint;
import me.hhjeong.springbootcms.security.handler.CustomAuthenticationFailureHandler;
import me.hhjeong.springbootcms.security.handler.CustomAuthenticationSuccessHandler;
import me.hhjeong.springbootcms.security.handler.CustomLogoutHandler;
import me.hhjeong.springbootcms.security.handler.CustomLogoutSuccessHandler;
import me.hhjeong.springbootcms.security.jwt.JwtFilter;
import me.hhjeong.springbootcms.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import me.hhjeong.springbootcms.security.provider.CustomAuthenticationProvider;
import me.hhjeong.springbootcms.security.service.SecurityResourceService;
import org.springframework.context.annotation.Bean;
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
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  public static final String CUSTOM_DEFAULT_FILTER_PROCESSES_URL = "/api/login";
  private static final String[] permitAllResources = {"/", "/api/login"};
  public static final String LOGOUT_URL = "/api/logout";

  private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
  private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
  private final CustomAuthenticationProvider provider;
  private final SecurityResourceService securityResourceService;
  private final JwtFilter jwtFilter;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  private final CustomLogoutHandler customLogoutHandler;
  private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .authenticationProvider(this.provider);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        //.antMatchers(HttpMethod.OPTIONS, "/**")

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
        .cors()
        .and()

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
        /*
        .authorizeRequests()
        .antMatchers("/api/account").hasAuthority("ROLE_USER")
        .antMatchers("/api/account/user-feature").hasAuthority("ROLE_USER")
        .antMatchers("/api/account/admin-feature").hasAuthority("ROLE_ADMIN")
        .anyRequest().authenticated()
        .and()
        */
        .exceptionHandling()
        .authenticationEntryPoint(customAuthenticationEntryPoint)
        .accessDeniedHandler(customAccessDeniedHandler)
        .and()

        .addFilterBefore(customAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(logoutFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class)
    ;
  }

  private LogoutFilter logoutFilter() {
    LogoutFilter logoutFilter = new LogoutFilter(customLogoutSuccessHandler, customLogoutHandler);
    logoutFilter.setFilterProcessesUrl(LOGOUT_URL);
    return logoutFilter;
  }

  protected CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter() throws Exception {
    CustomAuthenticationProcessingFilter filter = new CustomAuthenticationProcessingFilter(CUSTOM_DEFAULT_FILTER_PROCESSES_URL, customAuthenticationSuccessHandler, customAuthenticationFailureHandler);
    filter.setAuthenticationManager(super.authenticationManagerBean());
    return filter;
  }

  @Bean
  public PermitAllFilter customFilterSecurityInterceptor() throws Exception {
    PermitAllFilter permitAllFilter = new PermitAllFilter(permitAllResources);
    permitAllFilter.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());   // URL방식의 권한정보 설정
    permitAllFilter.setAccessDecisionManager(new AffirmativeBased(Arrays.asList(new RoleVoter()))); // AccessDecisionManager 설정
    permitAllFilter.setAuthenticationManager(super.authenticationManagerBean());
    return permitAllFilter;
  }

  @Bean
  public FilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() {
    return new UrlFilterInvocationSecurityMetadataSource(new UrlResourcesMapFactoryBean(securityResourceService).getObject(), securityResourceService);
  }

  // CORS 허용 적용
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOriginPattern("http://localhost");
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}
