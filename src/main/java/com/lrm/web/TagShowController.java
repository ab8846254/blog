package com.lrm.web;

import com.lrm.log.Tag;
import com.lrm.service.BlogService;
import com.lrm.service.TagService;
import com.lrm.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Administrator
 * 按标签展示
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;
    /**
     * 按标签展示列表
     * @param id
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping("/tags/{id}")
    public String Tag(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                             @PathVariable Long id, Pageable pageable, Model model){
        //先查询处所有的标签放在集合中
        List<Tag> tags = tagService.listTag();
        if(id==-1){
            id=tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        return "tags";


    }
}
