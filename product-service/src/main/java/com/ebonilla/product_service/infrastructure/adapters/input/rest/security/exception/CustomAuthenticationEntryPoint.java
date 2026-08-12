package com.ebonilla.product_service.infrastructure.adapters.input.rest.security.exception;

import com.ebonilla.product_service.infrastructure.adapters.input.rest.response.BaseResponse;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.util.ErrorResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = "The token is invalid or has expired.";

        BaseResponse<ErrorResponseUtil> body = BaseResponse.error(
                ErrorResponseUtil.handlerMessageError(
                        status,
                        message,
                        request.getRequestURI()
                )
        );

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    }
}
