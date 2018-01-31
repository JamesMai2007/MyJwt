package com.jm.authserver.config;

import com.jm.jwt.JsonWebToken;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Createy Administrator on 2018/1/30.
 */
@ConfigurationProperties("jm.jwt")
@Configuration
public class BeanConfig{

    @Bean(name="jsonWebToken")
    public JsonWebToken jsonWebToken(){
        JsonWebToken jsonWebToken = new JsonWebToken();
        jsonWebToken.setExpire(this.expire);
        jsonWebToken.setHeader(this.header);
        jsonWebToken.setSecret(this.secret);
        return jsonWebToken;
    }

    private String secret;
    private long expire;
    private String header;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
