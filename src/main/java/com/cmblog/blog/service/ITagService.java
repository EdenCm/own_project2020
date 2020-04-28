package com.cmblog.blog.service;

import com.cmblog.blog.po.Tag;
import com.cmblog.blog.po.Type;
import com.cmblog.blog.vo.TagTop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ITagService {
    int saveTag(Tag tag);
    Tag getTag(Long id);
//    Page<Tag> listTag(Pageable pageable);
    //
    List<Tag> listTag(String ids);

    int updateTag(Long id, Tag tag);
    void deleteTag(Long id);
    Tag getTagByName(String name);

    List<Tag> listTag();

    List<TagTop> listTagTop(Integer i);
}
