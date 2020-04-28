package com.cmblog.blog.web;

import com.cmblog.blog.po.Comment;
import com.cmblog.blog.po.User;
import com.cmblog.blog.service.IBlogService;
import com.cmblog.blog.service.ICommentService;
import com.cmblog.blog.service.impl.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.jnlp.UnavailableServiceException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class CommentController {

    @Autowired
    private ICommentService commentService;
    @Autowired
    private IBlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;
    // 查找评论内容
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){

        model.addAttribute("comments" ,commentService.listCommentByBlogID(blogId));


        return "blog :: commentList";
    }

    // 保存评论

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long blogId = comment.getBlogId();
        System.out.println(blogId);
//        comment.setBlog(blogService.getBlog(blogId));
//        comment.setAvatar(avatar);
        // 获取用户从session里
        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

}
