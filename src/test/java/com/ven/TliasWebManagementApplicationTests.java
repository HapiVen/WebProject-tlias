package com.ven;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class TliasWebManagementApplicationTests {

    @Test
    public void genJwt(){ //测试JWT令牌生成
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","Tom");

        String jwt = Jwts.builder()
                .setClaims(claims) //自定义内容(载荷)
                .signWith(SignatureAlgorithm.HS256, "venven") //签名算法
                .setExpiration(new Date(System.currentTimeMillis() + 24*3600*1000)) //有效期 1天
                .compact();

        System.out.println(jwt);
    }


    @Test
    public void parseJwt(){
        Claims claims = Jwts.parser()
                .setSigningKey("venven")//指定签名密钥（必须保证和生成令牌时使用相同的签名密钥）
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNjkwOTYwNDEwLCJ1c2VybmFtZSI6IlRvbSJ9.HSsYeYF0OdRYvOhKsgN6Y9FMnH7NKXvB3WvOPkaiu0A")
                .getBody();

        System.out.println(claims);
    }
}
