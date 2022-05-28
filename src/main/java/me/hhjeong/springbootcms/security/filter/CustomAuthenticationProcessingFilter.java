package me.hhjeong.springbootcms.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.hhjeong.springbootcms.security.dto.LoginDto;
import me.hhjeong.springbootcms.security.token.PreAuthorizationToken;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;

    protected CustomAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public CustomAuthenticationProcessingFilter(String defaultFilterProcessesUrl, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        super(defaultFilterProcessesUrl);
        this.authenticationSuccessHandler = successHandler;
        this.authenticationFailureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!isPost(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported : " + request.getMethod());
        }
        LoginDto loginDto = new ObjectMapper().readValue(request.getReader(), LoginDto.class);
        PreAuthorizationToken preAuthorizationToken = new PreAuthorizationToken(loginDto.getUsername(), loginDto.getPassword());

        return getAuthenticationManager().authenticate(preAuthorizationToken);
    }

    protected boolean isPost(String method) {
        return HttpMethod.resolve(method).equals(HttpMethod.POST);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        this.authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
    }
}
