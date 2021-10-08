package me.hhjeong.springbootcms.common.security.handler;

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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ApiExceptionEntity apiExceptionEntity = ApiExceptionEntity.builder()
            .status(HttpStatus.FORBIDDEN)
            .errorCode(ExceptionEnum.FORBIDDEN.getCode())
            .errorMessage(ExceptionEnum.FORBIDDEN.getMessage())
            .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(objectMapper.writeValueAsString(apiExceptionEntity));

    }

}
