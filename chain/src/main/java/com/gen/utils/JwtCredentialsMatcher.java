package com.gen.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;

@Component
public class JwtCredentialsMatcher implements CredentialsMatcher {
    /**
     * JwtCredentialsMatcher只需验证JwtToken内容是否合法
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        //获取用户的token
        String token = authenticationToken.getCredentials().toString();
        //获取用户的用户名
        String name = authenticationToken.getPrincipal().toString();
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtUtils.SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("name", name).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
