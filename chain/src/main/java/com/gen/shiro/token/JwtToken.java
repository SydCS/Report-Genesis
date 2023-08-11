package com.gen.shiro.token;

import com.gen.utils.JwtUtils;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 该类与UsernamePasswordToken差不多，都是AuthenticationToken接口的实现类
 * 目的是封装成UsernamePasswordToken让shiro进行登录、登出等操作
 * 目的就是构造一个AuthenticationToken对象以供shiro登录验证
 */
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;
    //加密后的 JWT token
    private String token;
    private String name;

    public JwtToken(String token){
        this.token = token;
        this.name = JwtUtils.getClaimFiled(token,"name");
    }

    @Override
    public Object getPrincipal() {
        return this.name;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
