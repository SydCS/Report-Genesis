package com.gen.shiro.realm;

import com.gen.shiro.realm.ShiroRealm;
import com.gen.entity.User;
import com.gen.service.UserService;
import com.gen.shiro.token.JwtToken;
import com.gen.service.UserService;
import com.gen.shiro.token.JwtToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 实现了认证和授权
 */
//@Component
public class JwtRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 限定这个 Realm 只处理我们自定义的 JwtToken
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 认证功能：此处的 SimpleAuthenticationInfo 可返回任意值，密码校验时不会用到它
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authcToken;
        if (jwtToken.getPrincipal() == null) {
            throw new AccountException("JWT token参数异常！");
        }

        // 从 JwtToken 中获取当前用户的用户名
        String name = jwtToken.getPrincipal().toString();
        // 查询数据库获取用户信息，此处使用 Map 来模拟数据库
        User user = userService.getUserByName(name);

        // 用户不存在
        if (user == null) {
            throw new UnknownAccountException("用户不存在！");
        }

        // 用户被锁定
//        if (user.getLocked()) {
//            throw new LockedAccountException("该用户已被锁定,暂时无法登录！");
//        }

        return new SimpleAuthenticationInfo(user, name, getName());
    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取当前用户
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        // 查询数据库，获取用户的角色信息
        // Set<String> roles = ShiroRealm.roleMap.get(currentUser.getName());
        // 查询数据库，获取用户的权限信息
        //    Set<String> perms = ShiroRealm.permMap.get(currentUser.getName());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //   info.setRoles(roles);
        //   info.setStringPermissions(perms);
        return info;
    }
}
