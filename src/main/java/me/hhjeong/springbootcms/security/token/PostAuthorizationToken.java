package me.hhjeong.springbootcms.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PostAuthorizationToken(Object userDetails, Object password, Collection<? extends GrantedAuthority> authorities) {
        super(userDetails, password, authorities);
    }
}
