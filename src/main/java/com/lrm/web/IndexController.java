package com.lrm.web;

import com.lrm.service.BlogService;
import com.lrm.service.TagService;
import com.lrm.service.TypeService;
import com.lrm.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * @author limi
 * @date 2017/10/13
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;

    /**
     * 前端列表展示页面接口
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTag(10));
        model.addAttribute("recommendBlogs",blogService.listBlogTop(8));
        return "index";
    }

    /**
     * 跳转页
     * @return
     */
    @RequestMapping("/blog/{id}")
    public String blog(@PathVariable Long id ,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    /**
     * 全局搜索
     * @return
     */
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model,@RequestParam String query){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return  "search";
    }
}
