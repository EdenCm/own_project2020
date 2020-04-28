package com.cmblog.blog.service.impl;

import com.cmblog.blog.NotFoundException;
import com.cmblog.blog.dao.ITypeDao;
import com.cmblog.blog.po.Type;
import com.cmblog.blog.service.ITypeService;
import com.cmblog.blog.vo.TypeTop;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements ITypeService {
    @Autowired
//    private TypeDao typeDao;
    private ITypeDao typeDao;

    @Transactional
    @Override
    public int  saveType(Type type) {
        return typeDao.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeDao.findOne(id);
    }

    @Transactional
    @Override
    public List<Type> listType() {
        return typeDao.findAll();
    }

    @Transactional
    @Override
    public int updateType(Long id, Type type) {
        Type t = typeDao.findOne(id);
        if (t == null){
            throw new NotFoundException("不存在该对象");
        }
        BeanUtils.copyProperties(type,t);
//        Object s = t;
        return typeDao.updateType(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeDao.delete(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.findByName(name);
    }
    // 前端标签模块查询全部,和后端查询类别下拉列表


    @Override
    public List<TypeTop> listTypeTop(Integer size) {
//        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
//        Pageable pageable = new PageRequest(0,size,sort);
        return typeDao.findTop(size);
    }
}
