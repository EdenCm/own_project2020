package com.cmblog.blog.service;

import com.cmblog.blog.po.User;


public interface IUserService {
    User checkUser(String username,String password);
}
