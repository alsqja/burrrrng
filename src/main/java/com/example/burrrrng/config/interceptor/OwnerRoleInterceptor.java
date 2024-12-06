package com.example.burrrrng.config.interceptor;

import com.example.burrrrng.constants.Const;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.UserRole;
import com.example.burrrrng.exception.UnauthorizedException;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@NonNullApi
public class OwnerRoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws UnauthorizedException {

        HttpSession session = request.getSession(false);
        
        if (session == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        User user = (User) request.getSession(false).getAttribute(Const.LOGIN_USER);

        if (user.getRole() != UserRole.OWNER) {
            throw new UnauthorizedException("관리자 권한이 필요합니다.");
        }

        return true;
    }
}