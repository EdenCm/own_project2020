package com.cmblog.blog.web.admin;

import com.cmblog.blog.po.Type;
import com.cmblog.blog.service.ITypeService;
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
public class TypeController {
    @Autowired
    private ITypeService typeService;

    /**
     * 点击导航栏中分类按钮，跳转到分类界面，并查询到所有分类信息展示
     * @param
     * @return
     */
    @GetMapping("/types/{pageNum}")
    public ModelAndView list(@PathVariable(value = "pageNum") int pageNum){
        PageHelper.startPage(pageNum,3);
        ModelAndView mv = new ModelAndView();
        List<Type> types = typeService.listType();
        PageInfo<Type> info = new PageInfo<>(types);
        mv.addObject("page",info);
        mv.setViewName("admin/types");
//        return "admin/types";
        return mv;
    }

    /**
     * 点击新增按钮，跳转新增页面
     * @param model
     * @return
     */
    @RequestMapping("/types/input")
    public String  input(Model model){
        model.addAttribute("type",new Type());
        return "admin/type-input";
    }

    /**
     * 新增类别方法
     * @param type
     * @param result
     * @param attributes 页面重定向之后发送的提示信息
     * @return
     */

    @PostMapping("/types")
    public String addTypes(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type typeByName = typeService.getTypeByName(type.getName());
        if (typeByName != null){
            result.rejectValue("name","nameError","不能添加重复分类");
        }
        if (result.hasErrors()){
            return "admin/type-input";
        }
         int t = typeService.saveType(type);
        System.out.println(t);
        if (t== 0){
            // 保存失败
            attributes.addFlashAttribute("message","操作失败");

        }else {
            //保存成功 提示
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/types/1";
    }

    /**
     * 点击编辑按钮后跳转到编辑类别页面编辑类别
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/type/{id}/input")
    public String editInput(@PathVariable Long id ,Model model){
        // 将id传入type-input页面
        model.addAttribute("type",typeService.getType(id));
        return "admin/type-input";
    }

    /**
     * 确认编辑
     * @param type
     * @param result
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Type typeByName = typeService.getTypeByName(type.getName());
        if (typeByName != null){
            result.rejectValue("name","nameError","不能添加重复分类");
        }
        if (result.hasErrors()){
            return "admin/type-input";
        }
        int t = typeService.updateType(id,type);
        System.out.println(t);
        if (t == 0){
            // 保存失败
            attributes.addFlashAttribute("message","更新失败");

        }else {
            //保存成功 提示
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types/1";
    }

    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:admin/types/1";
    }
}
