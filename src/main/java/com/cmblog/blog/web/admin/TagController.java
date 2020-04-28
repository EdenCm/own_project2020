package com.cmblog.blog.web.admin;

import com.cmblog.blog.po.Tag;
import com.cmblog.blog.po.Type;
import com.cmblog.blog.service.ITagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private ITagService tagService;

    /**
     * 点击导航栏中标签按钮，跳转到标签界面，并查询到所有标签信息展示
     * @param
     * @return
     */
    @GetMapping("/tags/{pageNum}")
    public ModelAndView list(@PathVariable(value = "pageNum") int pageNum){
        PageHelper.startPage(pageNum,3);
        ModelAndView mv = new ModelAndView();
        List<Tag> tags = tagService.listTag();
        PageInfo<Tag> info = new PageInfo<>(tags);
        mv.addObject("page",info);
        mv.setViewName("admin/tags");
//        return "admin/types";
        return mv;
    }

    /**
     * 点击新增按钮，跳转新增页面
     * @param model
     * @return
     */
    @RequestMapping("/tags/input")
    public String  input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tag-input";
    }

    /**
     * 新增类别方法
     * @param tag
     * @param result
     * @param attributes 页面重定向之后发送的提示信息
     * @return
     */

    @PostMapping("/tags")
    public String addTags(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tagByName = tagService.getTagByName(tag.getName());
        if (tagByName != null){
            result.rejectValue("name","nameError","不能添加重复标签");
        }
        if (result.hasErrors()){
            return "admin/tag-input";
        }
        int t = tagService.saveTag(tag);
        System.out.println(t);
        if (t== 0){
            // 保存失败
            attributes.addFlashAttribute("message","操作失败");

        }else {
            //保存成功 提示
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/tags/1";
    }

    /**
     * 点击编辑按钮后跳转到编辑类别页面编辑类别
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id ,Model model){
        // 将id传入tag-input页面
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tag-input";
    }

    /**
     * 确认编辑
     * @param tag
     * @param result
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Tag tagByName = tagService.getTagByName(tag.getName());
        if (tagByName != null){
            result.rejectValue("name","nameError","不能添加重复标签");
        }
        if (result.hasErrors()){
            return "admin/tag-input";
        }
        int t = tagService.updateTag(id,tag);
        System.out.println(t);
        if (t== 0){
            // 保存失败
            attributes.addFlashAttribute("message","更新失败");

        }else {
            //保存成功 提示
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags/1";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags/1";
    }
}
