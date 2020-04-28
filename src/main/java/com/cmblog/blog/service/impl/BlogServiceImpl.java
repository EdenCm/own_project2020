package com.cmblog.blog.service.impl;

import com.cmblog.blog.NotFoundException;
import com.cmblog.blog.dao.IBlogDao;
import com.cmblog.blog.dao.ITagDao;
import com.cmblog.blog.dao.IUserDao;
import com.cmblog.blog.po.Blog;
import com.cmblog.blog.po.Tag;
import com.cmblog.blog.po.User;
import com.cmblog.blog.service.IBlogService;
import com.cmblog.blog.utils.MarkdownUtils;
import com.cmblog.blog.utils.MyBeanUtils;
import com.cmblog.blog.vo.BlogQuery;
import com.cmblog.blog.vo.BlogQueryByCondition;
import com.cmblog.blog.vo.IndexPageBlog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogServiceImpl implements IBlogService {
    @Autowired
    private IBlogDao blogDao;
    @Autowired
    private ITagDao tagDao;
    @Autowired
    private IUserDao userDao;

    @Override
    public Blog getBlog(Long id) {
        return blogDao.findOne(id);
    }


    /**
     * 动态查询
     * @param
     * @param
     * @return
     */
    @Override
    public List<BlogQuery> listBlog(BlogQueryByCondition blogQueryByCondition) {
        return blogDao.findBlogByQuryCondition(blogQueryByCondition);
    }

    @Transactional
    @Override
    public int saveBlog(Blog blog) {

        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0); // 初始浏览次数
        // 保存标签-博客关系
        List<Long> id_list = converToList(blog.getTagIds());
        for (Long tag_id : id_list) {
            blogDao.saveTagAndBlog(tag_id,blog.getId());
        }
        int countSaveBlog = blogDao.save(blog);
        return countSaveBlog;
    }

    // 将字符数组转换为集合
    public  List<Long> converToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i = 0; i < idarray.length; i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
    @Transactional
    @Override
    public int updateBlog(Blog blog) {
        Blog b = blogDao.findOne(blog.getId());
//        if (b==null){
//            throw new NotFoundException("该博客不存在");
//        }
//        // 把blog复制给b
        BeanUtils.copyProperties(blog,b,MyBeanUtils.getNullPropertyNames(blog));// 过滤掉空的属性,只copy blog对象里有值的属性
////        b.setUpdateTime(new Date());
        int count = blogDao.updateBlog(b);
        // 删除t_blog_tag 中间表中含blog的行

        blogDao.delBlogAndTag(blog.getId());
        List<Long> id_list = converToList(blog.getTagIds());
        for (Long tag_id : id_list) {
            blogDao.saveTagAndBlog(tag_id,blog.getId());
        }
        return count;
    }
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogDao.delete(id);
        blogDao.delBlogAndTag(id);
    }

    @Override
    public List<Blog> listBlog() {
        return blogDao.findAll();
    }

    @Override
    public List<Blog> listRecommendBolgTop(Integer size) {
//        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
//        Pageable pageable = new PageRequest(0,size,sort);
        return blogDao.findTop(size);
    }

    // 全局搜索
    @Override
    public List<IndexPageBlog> listBlog(String query) {
        return blogDao.findByQuery(query);
    }
// 查看某一篇具体博客
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogDao.findOne(id);
        List<Tag> tags = tagDao.findTagsByBlogId(id);
        User user = userDao.findOneByBlogUserId(blog.getUserId());
        blog.setUser(user);
        blog.setTags(tags);

        if (blog== null){
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        // 更新浏览次数
        blogDao.updateViews(id);
        return b;
    }

    @Override
    public List<IndexPageBlog> listBlogByTypeId(Long typeId) {
        return blogDao.findBlogByTypeId(typeId);
    }

    @Override
    public List<IndexPageBlog> listBlogByTagId(Long tagId) {
        List<IndexPageBlog> blogByTagId = blogDao.findBlogByTagId(tagId);
        for (IndexPageBlog indexPageBlog : blogByTagId) {
            indexPageBlog.setTags(tagDao.findTagsByBlogId(indexPageBlog.getId()));
        }
        return blogByTagId;

    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogDao.findGroupYear();
        Map<String ,List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year,blogDao.findByYear(year));
        }
        return map;
    }

    @Override
    public Integer countBlog() {
        return  blogDao.count();
    }

    @Override
    public List<BlogQuery> listBlogQuery() {
        return blogDao.getAllBlogQuery();
    }

    @Override
    public List<IndexPageBlog> listIndexPageBlogs() {
        return blogDao.findIndexPageBlog();
    }


}
