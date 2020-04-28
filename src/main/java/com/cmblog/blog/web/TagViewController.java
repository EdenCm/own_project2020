package com.cmblog.blog.web;

import com.cmblog.blog.po.Tag;
import com.cmblog.blog.service.IBlogService;
import com.cmblog.blog.service.ITagService;
import com.cmblog.blog.vo.BlogQuery;
import com.cmblog.blog.vo.IndexPageBlog;
import com.cmblog.blog.vo.TagTop;
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
public class TagViewController {

    @Autowired
    ITagService tagService;

    @Autowired
    IBlogService blogService;

    @GetMapping("/tags/{id}/{pageNum}")
    public String findBlogByTag(@PathVariable Long id, Model model, @PathVariable(value = "pageNum") Integer pageNum){

        List<TagTop> tags = tagService.listTagTop(100);

        if (id == -1){
            id = tags.get(0).getTagId();
        }
        PageHelper.startPage(pageNum,8);
        List<IndexPageBlog> pageBlogList = blogService.listBlogByTagId(id);
        PageInfo<IndexPageBlog> pageInfo= new PageInfo<>(pageBlogList);
        model.addAttribute("page",pageInfo);
        model.addAttribute("tags",tags);
        model.addAttribute("activeTagId",id);
        return "tags";

    }
}
