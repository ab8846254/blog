package com.lrm.web;

import com.lrm.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 */
@Controller
public class ArchivesShowController {


    @Autowired
    private BlogService blogService;


    @RequestMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archivesBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }
}
