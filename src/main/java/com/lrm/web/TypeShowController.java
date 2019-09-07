package com.lrm.web;

import com.lrm.log.Type;
import com.lrm.service.BlogService;
import com.lrm.service.TypeService;
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
 */
@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    /**
     * 分类的分页查询
     * @param pageable
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/types/{id}")
    public String types(@PageableDefault(size = 8, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model,@PathVariable  Long id){
        //查询处所有的分类
        List<Type> types = typeService.listTypeTop(10000);
        if(id==-1){
            id=types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
