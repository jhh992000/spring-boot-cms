package me.hhjeong.springbootcms.security.handler;

import static me.hhjeong.springbootcms.security.handler.CustomAuthenticationSuccessHandler.REFRESH_TOKEN_PREFIX;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    String key = REFRESH_TOKEN_PREFIX + userDetails.getUsername();

    log.debug("logout - remove token : " + key);

    redisTemplate.delete(key);
  }

}
