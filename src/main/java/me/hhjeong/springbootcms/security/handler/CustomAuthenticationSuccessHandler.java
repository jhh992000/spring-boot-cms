package me.hhjeong.springbootcms.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import me.hhjeong.springbootcms.security.dto.TokenResponse;
import me.hhjeong.springbootcms.security.jwt.TokenProvider;
import me.hhjeong.springbootcms.security.token.PostAuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public static final String REFRESH_TOKEN_PREFIX = "REFRESH_TOKEN_";
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        UserDetails userDetails = (UserDetails) token.getPrincipal();
        String accessToken = tokenProvider.createToken(userDetails);
        String refreshToken = tokenProvider.createRefreshToken(userDetails);

        String key = REFRESH_TOKEN_PREFIX + userDetails.getUsername();
        redisTemplate.opsForValue().set(key, refreshToken);

        processResponse(response, new TokenResponse(accessToken, refreshToken));
    }

    private void processResponse(HttpServletResponse res, TokenResponse tokenResponse) throws IOException {
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(HttpStatus.OK.value());
        res.getWriter().write(objectMapper.writeValueAsString(tokenResponse));
    }

}
