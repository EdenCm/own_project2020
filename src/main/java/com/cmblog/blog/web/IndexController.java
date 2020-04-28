package com.cmblog.blog.web;

import com.cmblog.blog.NotFoundException;
import com.cmblog.blog.po.Blog;
import com.cmblog.blog.service.IBlogService;
import com.cmblog.blog.service.ITagService;
import com.cmblog.blog.service.ITypeService;
import com.cmblog.blog.service.impl.BlogServiceImpl;
import com.cmblog.blog.vo.BlogQuery;
import com.cmblog.blog.vo.IndexPageBlog;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

//@Controller("/bolg")
@Controller
public class IndexController {
    @Autowired
    private IBlogService blogService;

    @Autowired
    private ITypeService typeService;

    @Autowired
    private ITagService tagService;
    @GetMapping("/{pageNum}")
    public String blog(@PathVariable(value = "pageNum") int pageNum,
                       Model model){
        model.addAttribute("totalBlog",blogService.countBlog());
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        PageHelper.startPage(pageNum,8);
        List<IndexPageBlog> blogs = blogService.listIndexPageBlogs();
        PageInfo<IndexPageBlog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("page",pageInfo);
        model.addAttribute("recommendBlogs",blogService.listRecommendBolgTop(8));
        return "index";
    }
    /**
     * 博客搜索
     * @param
     * @param query
     * @param model
     * @return
     */
    @PostMapping("/search/{pageNum}")
    public String search(@PathVariable(value = "pageNum") int pageNum,
                         @RequestParam String query, Model model){
        PageHelper.startPage(1,8);
        List<IndexPageBlog> pageBlogList =  blogService.listBlog("%"+query+"%");
        PageInfo<IndexPageBlog> pageInfo = new PageInfo<>(pageBlogList);
        model.addAttribute("page",pageInfo);
        model.addAttribute("query",query);
        return "search";
    }


    @GetMapping("/blog/{id}")
    public String  blog(@PathVariable Long id,Model model){

        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }



}
