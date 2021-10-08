package me.hhjeong.springbootcms.common.security.controller;

import me.hhjeong.springbootcms.common.response.ApiException;
import me.hhjeong.springbootcms.common.response.ExceptionEnum;
import me.hhjeong.springbootcms.common.security.dto.TokenDto;
import me.hhjeong.springbootcms.common.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/api/v1/refreshToken")
    public ResponseEntity refreshToken(@RequestParam("token") String token) {
        if (!tokenProvider.validateToken(token)) {
            throw new ApiException(ExceptionEnum.SECURITY_02);
        }

        //TODO - refreshToken 서버에서 한번 더 검증 (redis)

        Authentication authentication = tokenProvider.getAuthentication(token);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String accessToken = tokenProvider.createToken(userDetails);
        String refreshToken = tokenProvider.createRefreshToken(userDetails);

        //TODO - refreshToken 서버에 저장 (to redis)

        TokenDto tokenDto = new TokenDto(accessToken, refreshToken);
        return ResponseEntity.ok().body(tokenDto);
    }

}
