package com.cmblog.blog.dao;

import com.cmblog.blog.po.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ICommentDao {

    List<Comment> findByBlogId(Long blogId);

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId);

    Comment findOne(Long parentCommentId);

    int save(Comment comment);

    List<Comment> findChildComment(Long id);
}
