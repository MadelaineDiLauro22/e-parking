package com.tallerwebi.helpers;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            if(request.getSession().getAttribute("id") == null) {
                response.sendRedirect("/eparking/login");
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if(request.getSession().getAttribute("id") != null) {

            Long userId = (Long) request.getSession().getAttribute("id");
            String userNickname = (String) request.getSession().getAttribute("nickName");

            modelAndView.addObject("id", userId);
            modelAndView.addObject("nickName", userNickname);
        }
    }

}
