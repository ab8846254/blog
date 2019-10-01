package com.lrm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 */
@Controller
public class AboutShowController {

    @RequestMapping("/about")
    public String about(){
        return "about";
    }
}
