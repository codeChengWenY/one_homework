package com.lagou.edu.controller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.lagou.edu.service.CodeService;
import com.lagou.edu.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName UserController
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-27 11:32
 * @Version V1.0
 **/
@RestController
@RequestMapping(value = "/user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private CodeService codeService;


    private Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();

    @PostMapping(value = "/register/{email}/{password}/{code}")
    public ResponseEntity register(@PathVariable("email") String email,
                                   @PathVariable("password") String password,
                                   @PathVariable("code") String code) {
        // 验证验证码
        Integer validate = codeService.validate(email, code);
        if (0 == validate) {
            // 注册
            userService.register(email, password);
            return ResponseEntity.ok(true);
        } else if (validate == 1) {
            return ResponseEntity.badRequest().body("验证码不正确");
        } else {
            return ResponseEntity.badRequest().body("验证码已过期");
        }
    }

    @GetMapping(value = "/isRegistered/{email}")
    public ResponseEntity isRegistered(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.isRegister(email));
    }

    @PostMapping(value = "/login/{email}/{password}")
    public ResponseEntity login(@PathVariable("email") String email,
                                @PathVariable("password") String password, HttpServletResponse response) {
        try {
            String userEmail = userService.login(email, password);
            String token = UUID.randomUUID().toString();
            cache.put(token, email);
            Cookie cookie = new Cookie("TOKEN", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.ok(userEmail);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping(value = "/info")
    public ResponseEntity info(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookie.getName(), "TOKEN")) {
                String value = cookie.getValue();
                return ResponseEntity.ok(cache.getIfPresent(value));
            }
        }
        return ResponseEntity.badRequest().body("未登录");

    }
}
