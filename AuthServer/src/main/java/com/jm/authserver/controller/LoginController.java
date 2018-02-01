package com.jm.authserver.controller;


import com.alibaba.fastjson.JSON;
import com.jm.authserver.toolkit.R;
import com.jm.jwt.JsonWebToken;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private JsonWebToken jsonWebToken;

    @ResponseBody
    @PostMapping(value = {"/login"})
    public Object login(Model model , String backurl ,  String username , String password , HttpServletRequest request , HttpServletResponse response){
        System.out.println("============= login");

        if("admin".equals(username) && "admin".equals(password)){
            Map<String,Object> params = new HashMap<>();
            params.put("username" , username);
            params.put("sex" , "Man");
            List<String> roles = new ArrayList<String>();
            roles.add("admin");
            List<String> perms = new ArrayList<String>();
            perms.add("sys:user:*");

        params.put("roles" , roles);
        params.put("perms" , perms);

        String token = jsonWebToken.generateToken("1", params);
        //response.setHeader("Authorization","Basic "+token);
        Map<String,Object> result = new HashMap<>();
        result.put("token" , token);
        result.put("msg" , "登录成功");
        return R.ok(result);
    }



        return R.error("账号或密码错误");

    }

    @GetMapping(value = {"/","index.html"})
    public String index(){
        return "index";
    }

    @GetMapping("/login.html")
    public String loginHtml(){
        return "login";
    }

    @GetMapping("/error")
    public String error(HttpServletRequest request){
        System.out.println("============= error");
        return "login";
    }

    @ResponseBody
    @GetMapping("/protected")
    public R viewProtected(HttpServletRequest request){
        Claims claims = (Claims)request.getAttribute("claims");

        System.out.println("============= protected !!!!");
        System.out.println("============= "+ JSON.toJSONString(claims));
        return R.ok("请求成功: "+JSON.toJSONString(claims));
    }
}
