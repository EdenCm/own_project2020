package com.cmblog.blog.web;

import com.cmblog.blog.po.Type;
import com.cmblog.blog.service.IBlogService;
import com.cmblog.blog.service.ITypeService;
import com.cmblog.blog.vo.BlogQuery;
import com.cmblog.blog.vo.BlogQueryByCondition;
import com.cmblog.blog.vo.IndexPageBlog;
import com.cmblog.blog.vo.TypeTop;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeViewController {

    @Autowired
    ITypeService typeService;

    @Autowired
    IBlogService blogService;
//    @GetMapping("/types")
//    public String findAllBlogByType(Model model,@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable){
//        // 先查询所有博客展示
//        model.addAttribute("page",blogService.listBlog(pageable));
//        // 查询所有分类，
//        List<Type> types = typeService.listType();
//        model.addAttribute("types",typeService.listTypeTop(types.size()));
//        return "types";
//    }

    @GetMapping("/types/{id}/{pageNum}")
    public String findBlogByType(@PathVariable(value = "id" ,required = true) Long id, @PathVariable(value = "pageNum", required = false) Integer pageNum,
                                 Model model){

        List<TypeTop> types = typeService.listTypeTop(100);

        if (id == -1 ){
            id = types.get(0).getTypeId();
        }
        if (pageNum== null || "".equals(pageNum)){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum,8);
        List<IndexPageBlog> pageBlogList = blogService.listBlogByTypeId(id);
        PageInfo<IndexPageBlog> pageInfo = new PageInfo<>(pageBlogList);
        model.addAttribute("page",pageInfo);
        model.addAttribute("types",types);
        model.addAttribute("activeTypeId",id);
        return "types";

    }
}
