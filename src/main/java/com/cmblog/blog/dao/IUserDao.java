package com.cmblog.blog.dao;

import com.cmblog.blog.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface IUserDao {
//    @Select(" select * from t_user where username= #{username} and password = #{password}")
    User findUserByUsernameAndPassword(@Param("username") String username,
                                       @Param("password") String password);

    User findOneByBlogUserId(Long id);
}
