package com.cmblog.blog.service;

import com.cmblog.blog.po.Type;
import com.cmblog.blog.vo.TypeTop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITypeService {
    int saveType(Type type);
    Type getType(Long id);
//    List<Type> listType();
    int updateType(Long id ,Type type);
    void deleteType(Long id);
    Type getTypeByName(String name);

    List<Type> listType();

    /**
     * 前端页面展示top的type
     * @param size
     * @return
     */
    List<TypeTop> listTypeTop(Integer size);


}
