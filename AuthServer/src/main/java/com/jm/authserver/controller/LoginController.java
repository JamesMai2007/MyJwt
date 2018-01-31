package com.jm.authserver.controller;


import com.jm.authserver.toolkit.R;
import com.jm.jwt.JsonWebToken;
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
    public Object login(Model model ,  String username , String password , HttpServletRequest request , HttpServletResponse response){
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


            return R.ok(jsonWebToken.generateToken(1L, params));
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
}
