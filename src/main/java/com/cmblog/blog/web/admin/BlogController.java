package com.cmblog.blog.web.admin;

import com.cmblog.blog.po.Blog;
import com.cmblog.blog.po.User;
import com.cmblog.blog.service.IBlogService;
import com.cmblog.blog.service.ITagService;
import com.cmblog.blog.service.ITypeService;
import com.cmblog.blog.vo.BlogQuery;
import com.cmblog.blog.vo.BlogQueryByCondition;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.github.pagehelper.PageHelper;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT = "admin/blogs-input";
    private static final String UPDATE = "admin/blogs-update";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs/1";

    @Value("${article.firstPicturePath}")
    private String firstPicturePath;

    @Autowired
    private IBlogService blogService;
    @Autowired
    private ITypeService typeService;

    @Autowired
    private ITagService tagService;

    /**
     * 点击首页博客或者查询所有翻页
     * @param pageNum
     * @param blog
     * @param model
     * @return
     */
    @GetMapping("blogs/{pageNum}")
    public String blogs(@PathVariable(value = "pageNum") Integer pageNum,
                        BlogQuery blog, Model model){

       PageHelper.startPage(pageNum,3);
        List<BlogQuery> allBlog = blogService.listBlogQuery();
        PageInfo<BlogQuery> pageInfo = new PageInfo<>(allBlog);
        model.addAttribute("types",typeService.listType());
        System.out.println(pageInfo);
        model.addAttribute("page",pageInfo);
        return LIST;
    }

    /**
     * 条件查询
     * @param
     * @param
     * @param model
     * @return
     */
    @PostMapping("blogs/search/{pageNum}")
    public String search(@PathVariable(value = "pageNum") Integer pageNum,
                         BlogQueryByCondition blogCondition, Model model){
        PageHelper.startPage(pageNum,3);
        List<BlogQuery> blogs = blogService.listBlog(blogCondition);
        PageInfo<BlogQuery> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("page",pageInfo);
        return "admin/blogs::blogList";
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }
    /**
     * 博客新增页面弹出
     */
    @GetMapping("blogs/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());
        setTypeAndTag(model);
        return INPUT;
    }

    /**
     * 博客新增提交确认
     * @return
     */
    @PostMapping("/blogs")
    public String post(Blog blog, @RequestParam(value="imgFile") MultipartFile imgFile,
                       RedirectAttributes attributes, HttpSession session){
        User user = (User) session.getAttribute("user");
        blog.setUserId(user.getId());
//        System.out.println(blog);

        // 上传首图图片文件
        if (imgFile.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = imgFile.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
//        String filePath = "E://temp-rainy//"; // 上传后的路径
        String filePath = firstPicturePath; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            imgFile.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String filename = "/temp-rainy/" + fileName;
//        model.addAttribute("filename", filename);
//        return "file";
        blog.setFirstPicture("/images/firstPictures/"+fileName);
//        blog.setType(typeService.getType(Long.parseLong(blog.getTypeId())));
//        blog.setTags(tagService.listTag(blog.getTagIds()));  // 要在blog里定义TagIds对象
        int b;

        b= blogService.saveBlog(blog);


        if(b == 0){
            attributes.addFlashAttribute("message","新增失败");

        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return REDIRECT_LIST;

    }
    /**
     * 编辑更新博客(展示到页面)
     */

    @GetMapping("/blogs/{id}/update")
    public String edit( @PathVariable Long id, Model model){
        setTypeAndTag(model);

        Blog blog = blogService.getBlog(id);
//        blog.init(); // 将List<tag> 转换为tag 的id 字符串”1,2，3“
//        blog.setTypeId(blog.getType().getId().toString());
//        int b ;
//        b = blogService.updateBlog(blog.getId(),blog);
        model.addAttribute("blog",blog);
        return UPDATE;
    }


    /**
     * 编辑确认，提交到服务器，并返回列表页
     */
    @PostMapping("/blogs/updated")
    public String confirmEdit(Model model,Blog blog,RedirectAttributes attributes){

        blog.setUpdateTime(new Date());

        int i = blogService.updateBlog(blog);
        if (i == 1){
            attributes.addFlashAttribute("message","更新成功");
        }else {
            attributes.addFlashAttribute("message","更新失败");
        }

        return REDIRECT_LIST;
    }
    /**
     * 删除博客
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        attributes.addFlashAttribute("message","删除成功");
        blogService.deleteBlog(id);
        return REDIRECT_LIST;
    }
}
