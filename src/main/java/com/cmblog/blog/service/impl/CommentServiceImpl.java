package com.cmblog.blog.service.impl;
import com.cmblog.blog.dao.ICommentDao;
import com.cmblog.blog.po.Comment;
import com.cmblog.blog.service.ICommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private ICommentDao commentDao;

    @Override
    public List<Comment> listCommentByBlogID(Long blogId) {

        // 拿到顶级的评论
        List<Comment> topComents = commentDao.findByBlogIdAndParentCommentNull(blogId);
        return eachComment(topComents);
    }

    @Override
    public int saveComment(Comment comment) {
        Long parentCommentId = comment.getParentCommentId();
//        if (parentCommentId != -1){
////            comment.setParentComment(commentDao.findOne(parentCommentId).get);
//        } else {
//            comment.setParentComment(null);
//        }
        comment.setCreateTime(new Date());
        return commentDao.save(comment);
    }
    /**
     * 循环每个顶级的评论节点
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }

        // 合并评论的各层子代到第一级子代集合中

        combineChildren(commentsView);
        return commentsView;

    }

    private List<Comment> tempRelys = new ArrayList<>();

    private void combineChildren(List<Comment> commentsView) {

        for (Comment comment : commentsView) {
            List<Comment> replys1 = commentDao.findChildComment(comment.getId());
            for (Comment comment1 : replys1) {
                // 循环迭代，找出子代存放在tempReplys中
                recursively(comment1);
            }
            // 修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempRelys);
            // 清除临时存放区
            tempRelys = new ArrayList<>();
        }
    }

    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的 对象
     */

    private void recursively(Comment comment) {
        tempRelys.add(comment);
        // 顶节点添加到临时存放的集合中
        List<Comment> replys = commentDao.findChildComment(comment.getId());
        if (replys.size() > 0){
            for (Comment reply : replys) {
                tempRelys.add(reply);
                if (commentDao.findChildComment(reply.getId()).size() > 0){
                    recursively(reply);
                }
            }
        }
    }
}
