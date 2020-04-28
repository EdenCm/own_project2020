package com.cmblog.blog.dao;

import com.cmblog.blog.po.Type;
import com.cmblog.blog.vo.TypeTop;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ITypeDao {

    Type findByName(String name);

    List<TypeTop> findTop(int size);

    int save(Type type);

    Type findOne(Long id);

    List<Type> findAll();

    void delete(Long id);
    int updateType(Type type);

//    List<Type> findAllTypeView();
}
