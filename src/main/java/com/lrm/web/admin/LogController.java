package com.lrm.web.admin;

import com.lrm.log.User;
import com.lrm.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/admin")
public class LogController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate RedisTemplate;

    /**
     * 跳转到登陆页面
     *
     * @return
     */
    @RequestMapping
    public String loginPage() {
        return "admin/login";
    }

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @RequestMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes) {

        User user = userService.checkUser(username, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message", "用户名或密码不正确");
            return "redirect:/admin";
        }

    }

    /**
     * 注册页面
     * @param username
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @RequestMapping("/register")
    public String register(@RequestParam Long Phone,@RequestParam String username,@RequestParam String password,HttpSession session,RedirectAttributes attributes){
        //生成6为数的验证码
        String code = RandomStringUtils.randomNumeric(6);
        //存放一份在redis中
        RedisTemplate.opsForValue().set(Phone+"code", code);
        //在消息队列里面存放一份

        return null;
    }

    /**
     * 注销操作
     *
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String loginOut(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
