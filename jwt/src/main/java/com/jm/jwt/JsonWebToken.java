package com.jm.jwt;

import com.jm.jwt.util.CertUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 */
//@ConfigurationProperties(prefix = "jm.jwt")
//@Component
public class JsonWebToken {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Long defaultExpire; //默认过期时间，unit: 秒

    private String header;  //参数 名称

    private String secret;  //字符密钥，生成端和验证端一样

    private Key privateKey; //证书密钥，用于生成端

    private Key publicKey;  //证书公钥，用于验证端

    private SecretType type;

    public String generateToken(String subject) {
        return generateToken(subject , null , null);
    }

    public String generateToken(String subject, Long expire) {
        return generateToken(subject , null , expire);
    }
    public String generateToken(String subject, Map<String,Object> params) {
        return generateToken(subject , params , null);
    }

    /**
     * 根据密钥生成jwt token , sign HS512
     * 或
     * 根据证书密钥生成jwt token , sign RS512
     * @param subject   用户唯一标识
     * @param params    签名主体内容
     * @param expire    token过期时间 ，unit: 秒
     * @return
     */
   public String generateToken(String subject, Map<String,Object> params , Long expire) {
       if(expire == null)
       {
           if(defaultExpire == null)
               throw new NullPointerException("defaultExpire不能为null");

           expire = defaultExpire;
       }

        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        if(params == null)
            params = new HashMap<>();

        if(type == null)
            throw new UnsupportedOperationException("请设置字符密钥或证书私钥");

       switch(type){
           case SECRET:
               return Jwts.builder()
                       .setClaims(params)
                       .setSubject(subject)
                       .setIssuedAt(nowDate)
                       .setExpiration(expireDate)
                       .signWith(SignatureAlgorithm.HS512, secret)
                       .compact();
           case PRIVATE_KEY:
               return Jwts.builder()
                       .setClaims(params)
                       .setSubject(subject)
                       .setIssuedAt(nowDate)
                       .setExpiration(expireDate)
                       .signWith(SignatureAlgorithm.RS512 , privateKey)
                       .compact();
           case PUBLIC_KEY:
                throw new UnsupportedOperationException("公钥不能创建TOKEN");
           default:
               throw new UnsupportedOperationException("请设置字符密钥或证书私钥");
       }


    }


    /**
     * 获取token里的主体内容，同时也是验证token是否有效
     * @param token
     * @return
     */
    public Claims getClaimByToken(String token) {

        if(type == null)
            throw new UnsupportedOperationException("请设置字符密钥或证书私钥或证书公钥");

        switch(type){
                case SECRET:
                        try {
                            return Jwts.parser()
                                    .setSigningKey(secret)
                                    .parseClaimsJws(token)
                                    .getBody();
                        }catch (Exception e){
                            logger.debug("validate is token error ", e);
                            return null;
                        }
                case PRIVATE_KEY:
                        try {
                            return Jwts.parser()
                                    .setSigningKey(privateKey)
                                    .parseClaimsJws(token)
                                    .getBody();
                        }catch (Exception e){
                            logger.debug("validate is token error ", e);
                            return null;
                        }
                case PUBLIC_KEY:
                        try {
                            return Jwts.parser()
                                    .setSigningKey(publicKey)
                                    .parseClaimsJws(token)
                                    .getBody();
                        }catch (Exception e){
                            logger.debug("validate is token error ", e);
                            return null;
                        }
                default:
                    throw new UnsupportedOperationException("请设置字符密钥或证书私钥");
            }



    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    public SecretType getType() {
        return type;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
        type = SecretType.SECRET;
    }

    public void setPrivateKey(String keyPath , String pwd) throws Exception {
        this.privateKey = CertUtils.getPrivateKey(keyPath , pwd);
        type = SecretType.PRIVATE_KEY;
    }

    public void setPublicKey(String keyPath) throws Exception {
        this.publicKey = CertUtils.getPublicKey(keyPath);
        type = SecretType.PUBLIC_KEY;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Long getDefaultExpire() {
        return defaultExpire;
    }

    public void setDefaultExpire(Long defaultExpire) {
        this.defaultExpire = defaultExpire;
    }

    enum SecretType{
        SECRET("字符密钥"),PRIVATE_KEY("证书私钥"),PUBLIC_KEY("证书公钥");
        String title;
        SecretType(String title) {
            this.title = title;
        }
    }

    public static void main(String[] args) throws Exception {
        JsonWebToken jwt = new JsonWebToken();
        jwt.setPrivateKey("E:\\study\\keytool\\PCKCS12_RSA_512\\private.p12" , "888888");
        String token = jwt.generateToken("1" , 60L);

        System.out.println(token);

        //jwt.setPublicKey("E:\\study\\keytool\\PCKCS12_RSA_512\\client.cer");
        Claims claims = jwt.getClaimByToken(token);
        System.out.println(claims);
        System.out.println(claims.getSubject());
    }
}
