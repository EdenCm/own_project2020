package com.cmblog.blog.service.impl;

import antlr.StringUtils;
import com.cmblog.blog.NotFoundException;
import com.cmblog.blog.dao.ITagDao;
import com.cmblog.blog.po.Tag;
import com.cmblog.blog.po.Type;
import com.cmblog.blog.service.ITagService;
import com.cmblog.blog.service.ITagService;
import com.cmblog.blog.vo.TagTop;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements ITagService {
    @Autowired
//    private TagDao tagDao;
    private ITagDao tagDao;

    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagDao.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagDao.findOne(id);
    }

    // 根据id集合获取全部tag对象
    @Override
    public List<Tag> listTag(String ids) {//ids=1,2,3
        List<Tag> list_tag = new ArrayList<>();
        List<Long> id_list = converToList(ids);
        for (Long id : id_list) {
            list_tag.add(tagDao.findOne(id)) ;
        }
        return list_tag;
    }
    // 将字符数组转换为集合
    public  List<Long> converToList(String ids){
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null){
            String[] idarray = ids.split(",");
            for (int i = 0; i < idarray.length; i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;

    }

    @Transactional
    @Override
    public int updateTag(Long id, Tag tag) {
        Tag t = tagDao.findOne(id);
        if (t == null){
            throw new NotFoundException("不存在该对象");
        }
        BeanUtils.copyProperties(tag,t);
//        Object s = t;
        return tagDao.updateTag(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagDao.delete(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.findByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return tagDao.findAllTagView();
    }

    @Override
    public List<TagTop> listTagTop(Integer size) {
//        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size"); // 按照blog的多少来排序
//        Pageable pageable = new PageRequest(0,size,sort);
        return tagDao.findTop(size);
    }


}
