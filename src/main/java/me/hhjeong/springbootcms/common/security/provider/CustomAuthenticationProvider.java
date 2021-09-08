package me.hhjeong.springbootcms.common.security.provider;

import me.hhjeong.springbootcms.common.security.exception.PasswordNotMatchException;
import me.hhjeong.springbootcms.common.security.token.PostAuthorizationToken;
import me.hhjeong.springbootcms.common.security.token.PreAuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        String username = token.getUsername();
        String password = token.getPassword();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (!isCorrectPassword(password, userDetails.getPassword())) {
            throw new PasswordNotMatchException();
        }

        return new PostAuthorizationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PreAuthorizationToken.class.isAssignableFrom(aClass);
    }

    private boolean isCorrectPassword(String loginPassword, String password) {
        return passwordEncoder.matches(loginPassword, password);
    }

}
