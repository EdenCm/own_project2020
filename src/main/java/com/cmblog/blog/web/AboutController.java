package com.cmblog.blog.web;

import com.cmblog.blog.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @Autowired
    private IBlogService blogService;
    @GetMapping("/about")
    public String about(){
        return "about";
    }

    /**
     * 底部最新推荐
     */
    @GetMapping("/footer/newblog")
    public  String footNewBlog(Model model){
        System.out.println("foot");
        model.addAttribute("newblogs",blogService.listRecommendBolgTop(3));
        return "_fragments :: newBlogList";
    }
}
