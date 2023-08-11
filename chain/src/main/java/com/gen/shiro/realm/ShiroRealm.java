package com.gen.shiro.realm;

import com.gen.entity.User;
import com.gen.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 同时开启身份验证和权限验证，需要继承 AuthorizingRealm
 * 并实现其  doGetAuthenticationInfo()和 doGetAuthorizationInfo 两个方法
 */
@Log4j2
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;


    /**
     * 限定这个 Realm 只处理 UsernamePasswordToken
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 查询数据库，将获取到的用户安全数据封装返回
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 从 token 中获取当前用户
        String name = token.getPrincipal().toString();

        // 查询数据库获取用户信息
        User user = userService.getUserByName(name);
        log.info("realm: {}", user);

        // 用户不存在
        if (user == null) {
            log.warn("用户不存在！");
            throw new UnknownAccountException("用户不存在！");
        }

        // 用户被锁定
//        if (user.getLocked()) {
//            log.warn("该用户已被锁定,暂时无法登录！");
//            throw new LockedAccountException("该用户已被锁定,暂时无法登录！");
//        }

        /**
         * 将获取到的用户数据封装成 AuthenticationInfo 对象返回，此处封装为 SimpleAuthenticationInfo 对象。
         *  参数1. 认证的实体信息，可以是从数据库中获取到的用户实体类对象或者用户名?user
         *  参数2. 查询获取到的登录密码
         *  参数3. 盐值
         *  参数4. 当前 Realm 对象的名称，直接调用父类的 getName() 方法即可
         */
        return new SimpleAuthenticationInfo(name,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName());
    }

    /**
     * 查询数据库，将获取到的用户的角色及权限信息返回
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info(principals);

        String principal = "";
        Pattern pattern = Pattern.compile("name=([^,]+)");
        Matcher matcher = pattern.matcher(principals.toString());
        if (matcher.find()) {
            principal = matcher.group(1);
        }

        //获取当前用户身份信息
//        String principal =
//                principals.getPrimaryPrincipal().toString();

        //调用业务层获取用户的角色信息
        List<String> roles = userService.getUserRole(principal);
        log.info("{} - roles: {}", principal, roles);

        //创建对象，存储当前登录的用户的权限和角色
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //存储角色
        info.addRoles(roles);
//        info.addRole("admin");

        return info;
    }
}

