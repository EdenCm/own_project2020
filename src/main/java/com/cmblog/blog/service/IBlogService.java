package com.cmblog.blog.service;

import com.cmblog.blog.po.Blog;
import com.cmblog.blog.vo.BlogQuery;
import com.cmblog.blog.vo.BlogQueryByCondition;
import com.cmblog.blog.vo.IndexPageBlog;
import com.sun.crypto.provider.BlowfishKeyGenerator;
import org.jboss.jandex.Index;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

public interface IBlogService {
    /**
     * 根据id查询一个博客
     * @param id
     * @return
     */
    Blog getBlog(Long id);

    /**
     * 由条件查询博客
     * @param
     * @return
     */
    List<BlogQuery> listBlog(BlogQueryByCondition blogQueryByCondition);

    /*
    保存博客
     */
    int saveBlog(Blog blog);

    /**
     * 更新博客
     * @param id
     * @param blog
     * @return
     */
    int updateBlog(Blog blog);

    /**
     * 删除博客
     * @param id
     */
    void deleteBlog(Long id);

    /**
     * 前端查询所有博客
     */

    List<Blog> listBlog();


    /**
     * 前端按时间更新顺序选出前10条最博客
     */

    List<Blog> listRecommendBolgTop(Integer size);

    /**
     * 搜索博客
     */

    List<IndexPageBlog> listBlog(String query);

    /**
     * 获取博客内容，将markdown转换为html
     */

    Blog getAndConvert(Long id );


    /**
     * 由typeId查找博客
     */
    List<IndexPageBlog> listBlogByTypeId(Long typeId);

    /**
     * 由tagId查找博客
     * @param tagId
     * @return
     */

    List<IndexPageBlog> listBlogByTagId(Long tagId);
    /**
     * 归档查询
     */

    Map<String,List<Blog>> archiveBlog();

    Integer countBlog();

    List<BlogQuery> listBlogQuery();

    List<IndexPageBlog> listIndexPageBlogs();
}
