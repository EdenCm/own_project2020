package com.cmblog.blog.dao;


import com.cmblog.blog.po.Blog;
import com.cmblog.blog.vo.BlogQuery;
import com.cmblog.blog.vo.BlogQueryByCondition;
import com.cmblog.blog.vo.IndexPageBlog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Mapper
@Repository
public interface IBlogDao {

    List<Blog> findTop(Integer size);

    /**
     * 前端主页搜索博客
     */

    List<IndexPageBlog> findByQuery(String query);

    int updateViews(Long id);

    List<String> findGroupYear();


    List<Blog> findByYear(String year);

    Blog findOne(Long id);

    List<Blog> findAll();

    int save(Blog blog);

    void saveTagAndBlog(@Param("tagId") Long typeId,@Param("blogId") Long blogId);

    void delete(Long id);

    Integer count();


    List<BlogQuery> findBlogByQuryCondition(BlogQueryByCondition blogQueryByCondition);


    List<BlogQuery> getAllBlogQuery();


    int updateBlog(Blog blog);

    void delBlogAndTag(Long blogId);

    List<IndexPageBlog> findIndexPageBlog();

    List<Blog> viewOneBlog(Long id);

    // 由typeId查找博客
    List<IndexPageBlog> findBlogByTypeId(Long id);
    List<IndexPageBlog> findBlogByTagId(Long id);
}
