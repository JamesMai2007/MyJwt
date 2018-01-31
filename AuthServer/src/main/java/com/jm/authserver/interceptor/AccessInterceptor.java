package com.jm.authserver.interceptor;

import com.jm.jwt.JsonWebToken;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/1/30.
 */
@Component
public class AccessInterceptor implements HandlerInterceptor {

    @Autowired
    private JsonWebToken jsonWebToken;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String jwtToken = httpServletRequest.getHeader(jsonWebToken.getHeader());
        if(StringUtils.isBlank(jwtToken))
            jwtToken = httpServletRequest.getParameter(jsonWebToken.getHeader());

        if(!StringUtils.isBlank(jwtToken)){
            Claims claims = jsonWebToken.getClaimByToken(jwtToken);
            if(claims instanceof Claims && !jsonWebToken.isTokenExpired(claims.getExpiration())){
                return true;
            }
        }

        httpServletResponse.sendRedirect("/login.html");

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
