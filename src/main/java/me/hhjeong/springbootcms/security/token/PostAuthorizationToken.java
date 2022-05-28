package me.hhjeong.springbootcms.security.token;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PostAuthorizationToken(Object userDetails, Object password, Collection<? extends GrantedAuthority> authorities) {
        super(userDetails, password, authorities);
    }
}
