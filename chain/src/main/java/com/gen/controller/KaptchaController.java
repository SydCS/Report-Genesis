package com.gen.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Api(tags = "验证码")
@Controller
public class KaptchaController {
    @Autowired
    private DefaultKaptcha kaptcha;

    @GetMapping("/common/kaptcha")
    @ApiOperation(value = "获得验证码")
    public void getKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse resp) throws Exception {
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
        try {
            // 生成验证码字符串并保存到session中
            String code = kaptcha.createText();
            httpServletRequest.getSession().setAttribute("verify_code", code);

            BufferedImage challenge = kaptcha.createImage(code);
            ImageIO.write(challenge, "jpg", imgOutputStream);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        byte[] kaptchaOutputStream = imgOutputStream.toByteArray();
        resp.setHeader("Cache-Control", "no-store");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = resp.getOutputStream();
        responseOutputStream.write(kaptchaOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
