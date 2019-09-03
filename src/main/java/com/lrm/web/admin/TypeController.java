package com.lrm.web.admin;

import com.lrm.log.Type;
import com.lrm.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


/**
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/admin")
public class TypeController {

   @Autowired
    private TypeService typeService;

    /**
     * 分页显示所有分类信息的接口
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value ="/types",method = RequestMethod.GET)
    public String list(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));

        return "admin/types";
    }

    /**
     * 跳转方法 当用户点击增加分类后跳转页面
     * @return
     */
    @RequestMapping(value = "/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /**
     * 根据ID跳转回显出要修改的分类名称
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/types/{id}/input",method = RequestMethod.GET)
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.findTypeAndId(id));
        return "admin/types-input";
    }

    /**
     * 新增分类方法
     * @param type
     * @param result
     * @param attributes
     * @return
     */
    @RequestMapping(value = "/types",method = RequestMethod.POST)
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type byName = typeService.getTypeByName(type.getName());
        if(byName!=null){
            //如果查到了说明已经存在该分类了不能再次添加
            result.rejectValue("name","nameError","分类名称已经存在");


        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type type1 = typeService.saveType(type);
        if(type1==null){
            //如果等于null是保存失败
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }



    /**
     * 更新分类
     * @param type
     * @param result
     * @param attributes
     * @return
     */
    @RequestMapping(value = "/types/{id}",method = RequestMethod.POST)
    public String editpost(@Valid Type type, BindingResult result,@PathVariable Long id ,RedirectAttributes attributes){
        Type byName = typeService.getTypeByName(type.getName());
        if(byName!=null){
            //如果查到了说明已经存在该分类了不能再次添加
            result.rejectValue("name","nameError","分类名称已经存在");


        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type type1 = typeService.updateType(id,type);
        if(type1==null){
            //如果等于null是保存失败
            attributes.addFlashAttribute("message","修改失败");
        }else{
            attributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 根据ID删除分类
     * @param id
     * @return
     */
    @RequestMapping(value = "/types/{id}/delete",method = RequestMethod.GET)
    public String deleteType(@PathVariable Long id,RedirectAttributes attributes){
        Type type = typeService.findTypeAndId(id);
        if(type!=null){
            //通过I先查询下是否存在该分类若存在则获取到该分类的ID进行删除
            Long typeId = type.getId();
            //通过分类ID删除分类
            typeService.deleteType(typeId);
            attributes.addFlashAttribute("message","删除分类成功");
            //删除完成后跳转到 列表页面
            return "redirect:/admin/types";
        }else {
            //若等于null则代表分类ID错误 其实应该不会出现该情况除非用户手动修改了URL地址栏的ID值
            attributes.addFlashAttribute("message","查询不到该分类");
            //跳转到列表页面
            return "redirect:/admin/types";
        }

    }
}
