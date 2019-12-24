package com.lrm.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
public class LoginInterCeptor extends HandlerInterceptorAdapter {
    /**
     * 拦截器，拦截未登陆的
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            if(request.getSession().getAttribute("user")==null){
                response.sendRedirect("/admin");
                return false;
            }

        return true;
    }
}
