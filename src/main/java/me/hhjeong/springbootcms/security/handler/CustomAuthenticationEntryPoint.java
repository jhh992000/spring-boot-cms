package me.hhjeong.springbootcms.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import me.hhjeong.springbootcms.common.response.ApiExceptionEntity;
import me.hhjeong.springbootcms.common.response.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ApiExceptionEntity apiExceptionEntity = ApiExceptionEntity.builder()
            .status(HttpStatus.UNAUTHORIZED)
            .errorCode(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode())
            .errorMessage(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getMessage())
            .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(objectMapper.writeValueAsString(apiExceptionEntity));
    }
}
