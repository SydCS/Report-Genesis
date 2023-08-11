package com.gen.controller;

import com.gen.dto.UserLoginRequest;
import com.gen.dto.UserRegisterRequest;
import com.gen.entity.User;
import com.gen.service.UserService;
import com.gen.utils.JwtUtils;
import com.gen.utils.ResultUtil;
import com.gen.vo.Result;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "用户")
@RestController
@RequestMapping("/user")
@CrossOrigin()
@Log4j2
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/toLogin")
    public Result toLogin() {
        return ResultUtil.unauthorized("请登录");
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result login(@RequestBody UserLoginRequest loginRequest) {

        String username = loginRequest.getName();
        String password = loginRequest.getPassword();

        //1获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //2封装请求数据到token
        AuthenticationToken token = new UsernamePasswordToken(username, password);

        //3调用login方法进行登录认证
        try {
            subject.login(token);
            // 若登录成功，签发 JWT token
            String jwtToken = JwtUtils.sign(username, JwtUtils.SECRET);
            // 将签发的 JWT token 设置到 HttpServletResponse 的 Header 中
//            response.setHeader(JwtUtils.AUTH_HEADER, jwtToken);
            log.info("{} 登录成功", loginRequest);
            return ResultUtil.success(jwtToken);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            log.warn("{} 用户名不存在", loginRequest);
            return ResultUtil.define(401, "用户名不存在", null);
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            log.warn("{} 密码错误", loginRequest);
            return ResultUtil.define(402, "密码错误", null);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            log.warn("登录失败");
            return ResultUtil.error("登录失败");
        }
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册")
    @CrossOrigin(originPatterns = "*", allowCredentials = "true")
    public Result register(@RequestBody UserRegisterRequest registerRequest,
                           HttpServletRequest request) {
        // 验证
        String verificationCode = registerRequest.getCode();
        String sessionCode = request.getSession().getAttribute("verify_code") + "";
        if (!verificationCode.equals(sessionCode)) {
            return ResultUtil.error("验证码错误");
        }

        String username = registerRequest.getName();
        String password = registerRequest.getPassword();
        boolean exist = userService.isExist(username);
        if (exist) {
            return ResultUtil.error("用户名已被占用");
        }

        User user = new User();
        BeanUtils.copyProperties(registerRequest, user);

        // 生成随机salt
        SecureRandomNumberGenerator saltGenerator = new SecureRandomNumberGenerator();
        String salt = saltGenerator.nextBytes(8).toHex();
        user.setSalt(salt);

        // 得到MD5加密后的密码
        String encoded = new Md5Hash(password, salt, 3).toHex();
        user.setPassword(encoded);

        userService.addUser(user);
        log.info(user);

        return ResultUtil.success();
    }

    @GetMapping("/logout")
    @ApiOperation(value = "退出登录")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultUtil.success();
    }

}
