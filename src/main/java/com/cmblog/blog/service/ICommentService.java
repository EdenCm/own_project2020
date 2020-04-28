package com.cmblog.blog.service;

import com.cmblog.blog.po.Comment;

import java.util.List;

public interface ICommentService {

    /**
     * 获取评论列表
     */

    List<Comment> listCommentByBlogID(Long blogId);

    /**
     * 保存评论
     */

    int saveComment(Comment comment);
}
