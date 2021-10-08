package me.hhjeong.springbootcms.common.security.controller;

import static me.hhjeong.springbootcms.common.security.handler.CustomAuthenticationSuccessHandler.REFRESH_TOKEN_PREFIX;

import me.hhjeong.springbootcms.common.response.ApiException;
import me.hhjeong.springbootcms.common.response.ExceptionEnum;
import me.hhjeong.springbootcms.common.security.dto.RefreshTokenRequest;
import me.hhjeong.springbootcms.common.security.dto.TokenResponse;
import me.hhjeong.springbootcms.common.security.jwt.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    private static final Logger logger = LoggerFactory.getLogger(JwtController.class);

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/api/refreshToken")
    public ResponseEntity refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String clientRefreshToken = refreshTokenRequest.getRefreshToken();

        //refreshToken 1차 검증
        if (!tokenProvider.validateToken(clientRefreshToken)) {
            throw new ApiException(ExceptionEnum.SECURITY_02);
        }

        Authentication authentication = tokenProvider.getAuthentication(clientRefreshToken);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String key = REFRESH_TOKEN_PREFIX + userDetails.getUsername();
        String savedRefreshToken = redisTemplate.opsForValue().get(key);

        //refreshToken 2차 검증 (redis에 저장된 토큰)
        if (!tokenProvider.validateToken(savedRefreshToken)) {
            throw new ApiException(ExceptionEnum.SECURITY_02);
        }

        String accessToken = tokenProvider.createToken(userDetails);
        String refreshToken = tokenProvider.createRefreshToken(userDetails);

        TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);
        logger.debug("tokenDto : {}", tokenResponse);

        return ResponseEntity.ok().body(tokenResponse);
    }

}
