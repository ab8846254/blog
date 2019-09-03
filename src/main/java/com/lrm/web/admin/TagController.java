package com.lrm.web.admin;

import com.lrm.dao.TagReposIyoy;
import com.lrm.log.Tag;
import com.lrm.log.Type;
import com.lrm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/admin")
public class TagController {


    @Autowired
    private TagService tagService;

    /**
     * 分页显示所有标签信息的接口
     *
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    public String list(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));

        return "admin/tag";
    }

    /**
     * 跳转方法 当用户点击增加标签后跳转页面
     *
     * @return
     */
    @RequestMapping(value = "/tag/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/Tag-input";
    }

    /**
     * 根据ID跳转回显出要修改的标签名称
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/tag/{id}/input", method = RequestMethod.GET)
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.findByTagAndId(id));
        return "admin/Tag-input";
    }

    /**
     * 新增标签方法
     *
     * @param tag
     * @param result
     * @param attributes
     * @return
     */
    @RequestMapping(value = "/tag", method = RequestMethod.POST)
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag tagName = tagService.getTagByName(tag.getName());
        if (tagName != null) {
            //如果查到了说明已经存在该标签了不能再次添加
            result.rejectValue("name", "nameError", "分类名称已经存在");


        }
        if (result.hasErrors()) {
            return "admin/Tag-input";
        }
        Tag tag1 = tagService.saveTag(tag);
        if (tag1 == null) {
            //如果等于null是保存失败
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tag";
    }


    /**
     * 更新标签
     *
     * @param tag
     * @param result
     * @param attributes
     * @return
     */
    @RequestMapping(value = "/tag/{id}", method = RequestMethod.POST)
    public String editpost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Tag tagName = tagService.getTagByName(tag.getName());
        if (tagName != null) {
            //如果查到了说明已经存在该标签了不能再次添加
            result.rejectValue("name", "nameError", "标签名称已经存在");


        }
        if (result.hasErrors()) {
            return "admin/Tag-input";
        }
        Tag tag1 = tagService.updateType(id, tag);
        if (tag1 == null) {
            //如果等于null是保存失败
            attributes.addFlashAttribute("message", "修改失败");
        } else {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/tag";
    }

    /**
     * 根据ID删除标签
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/tag/{id}/delete", method = RequestMethod.GET)
    public String deleteTag(@PathVariable Long id, RedirectAttributes attributes) {
        Tag tag = tagService.findByTagAndId(id);
        if (tag != null) {
            //通过I先查询下是否存在该标签若存在则获取到该标签的ID进行删除
            Long tagId = tag.getId();
            //通过分类ID删除标签
            tagService.deleteTagByIAndId(tagId);
            attributes.addFlashAttribute("message", "删除分类成功");
            //删除完成后跳转到 列表页面
            return "redirect:/admin/tag";
        } else {
            //若等于null则代表标签ID错误 其实应该不会出现该情况除非用户手动修改了URL地址栏的ID值
            attributes.addFlashAttribute("message", "查询不到该分类");
            //跳转到列表页面
            return "redirect:/admin/tag";
        }

    }


}
