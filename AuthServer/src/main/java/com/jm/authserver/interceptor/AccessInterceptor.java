package com.jm.authserver.interceptor;

import com.jm.jwt.JsonWebToken;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/1/30.
 */
public class AccessInterceptor implements HandlerInterceptor {

    @Autowired
    private JsonWebToken jwt;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String jwtToken = httpServletRequest.getHeader(jwt.getHeader());
        if(StringUtils.isBlank(jwtToken))
            jwtToken = httpServletRequest.getParameter(jwt.getHeader());

        if(!StringUtils.isBlank(jwtToken)){
            Claims claims = jwt.getClaimByToken(jwtToken);
            if(claims instanceof Claims && !jwt.isTokenExpired(claims.getExpiration())){
                return true;
            }
        }

        httpServletResponse.sendRedirect("/login.jsp");

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
