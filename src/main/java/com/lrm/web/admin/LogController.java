package com.lrm.web.admin;

import com.lrm.log.User;
import com.lrm.service.UserService;
import com.lrm.util.HibernateValidatorUtils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private HibernateValidatorUtils hibernateValidatorUtils;


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
     * @param session
     * @param attributes
     * @return
     */
    @RequestMapping("/register")
    public String register(User user, HttpSession session, RedirectAttributes attributes){

       // String user1 = userService.register(user);
        //校验字段是否填写完整
        boolean checkUserForm = hibernateValidatorUtils.checkUserForm(user, attributes);
        if(checkUserForm){
            User user1 = userService.checkUserNameAndPhon(user.getUsername(),user.getPhone());
            if(user1==null){
                String user2 = userService.register(user);
                if(user2!=null){
                    attributes.addFlashAttribute("registermessage", "注册成功");
                    return "redirect:/admin";
                }else {
                    attributes.addFlashAttribute("failmessage", "注册失败请稍后再试试");
                    return "redirect:/admin";
                }
            }else {
                attributes.addFlashAttribute("registerfailmessage", "注册失败{用户已注册或手机号已使用}");
                return "redirect:/admin";
            }
        }else {

            return "redirect:/admin";
        }


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
