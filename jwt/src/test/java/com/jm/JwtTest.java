package com.jm;

import com.alibaba.fastjson.JSON;
import com.jm.jwt.JsonWebToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTest{
    @Autowired
    private JsonWebToken jwt;

    @Test
    public void test(){
        Map<String,Object> p = new HashMap<String, Object>();
        p.put("kkk" , "1111");
        String token = jwt.generateToken(1L , p);
        System.out.println(token);
        System.out.println(JSON.toJSONString(jwt.getClaimByToken(token)));
    }
}
