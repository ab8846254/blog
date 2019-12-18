package com.lrm.web.admin;

import com.lrm.log.User;
import com.lrm.pojo.Blog;
import com.lrm.service.BlogService;
import com.lrm.service.TagService;
import com.lrm.service.TypeService;
import com.lrm.service.UserService;
import com.lrm.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.Lob;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    /**
     * 返回到列表页面
     *
     * @return
     */
    @RequestMapping("/blogs")
    public String list(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Long id = user.getId();
        blog.setUserId(id);
        //查询对应用户的对于分类列表
        model.addAttribute("types", typeService.listType(id));
        model.addAttribute("page", blogService.listBlog(pageable, blog,request));

        return "admin/blogs";
    }

    /**
     * 返回到列表页面动态查询加载
     *
     * @return
     */
    @RequestMapping("/blogs/search")
    public String search(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model,HttpServletRequest request) {

        model.addAttribute("page", blogService.listBlog(pageable, blog,request));
        return "admin/blogs :: blogList";
    }

    /**
     * 跳转到发布页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/blogs/input")
    public String input(Model model,HttpServletRequest request, BlogQuery blog) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Long id = user.getId();
        model.addAttribute("types", typeService.listType(id));
        model.addAttribute("tags", tagService.listTag(request,blog));
        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }

    /**
     * 跳转到修改页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("types", typeService.listType(user.getId()));
        model.addAttribute("tags", tagService.listTag(user.getId()));
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return "admin/blogs-input";
    }

    /**
     * 发布文章
     *
     * @return
     */
    @RequestMapping(value = "/blogs", method = RequestMethod.POST)
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.findTypeAndId(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog blog1 = blogService.saveBlog(blog);
        if (blog1 != null) {
            attributes.addFlashAttribute("message", "发布成功");
        } else {
            attributes.addFlashAttribute("message", "发布失败");
        }
        return "redirect:/admin/blogs";
    }

    /**
     * 删除博客，从前端直接获取博客值进行删除就好
     *
     * @param id
     * @return
     */
    @RequestMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes attributes) {
        if (!"".equals(id) && id != null) {
            blogService.deleteBlog(id);
            attributes.addFlashAttribute("message", "删除博客成功");
            return "redirect:/admin/blogs";
        } else {
            attributes.addFlashAttribute("message", "博客ID不能为空");
            return "redirect:/admin/blogs";
        }
    }
}
