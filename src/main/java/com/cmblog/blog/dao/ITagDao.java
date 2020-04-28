package com.cmblog.blog.dao;

import com.cmblog.blog.po.Tag;
import com.cmblog.blog.vo.TagTop;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
@Mapper
public interface ITagDao {

    Tag findByName(String name);

    List<TagTop> findTop(Integer size);

    int save(Tag tag);

    Tag findOne(Long id);

//    List<Tag> findAllByIds(List<Long> converToList);

    void delete(Long id);

    List<Tag> findAllTagView();

    int updateTag(Tag tag);
    List<Tag> findTagsByBlogId(Long id);
}
