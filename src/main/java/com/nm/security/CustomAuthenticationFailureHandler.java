package com.nm.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        String errorMessage = "Đăng nhập thất bại. Vui lòng thử lại.";

        if (exception instanceof BadCredentialsException) {
            errorMessage = "Sai tên đăng nhập hoặc mật khẩu.";
        } else if (exception instanceof LockedException) {
            errorMessage = "Tài khoản của bạn đã bị khóa.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "Tài khoản của bạn chưa được kích hoạt.";
        } else {
            errorMessage = exception.getMessage();
        }

        request.getSession().setAttribute("error", errorMessage);
        if (request.getRequestURI().startsWith("/admin")) {
            response.sendRedirect("/admin/login?error=true");
        } else {
            response.sendRedirect("/login?error=true");
        }
    }
}
