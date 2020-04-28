package com.cmblog.blog.service.impl;

import com.cmblog.blog.dao.IUserDao;
import com.cmblog.blog.po.User;
import com.cmblog.blog.service.IUserService;
import com.cmblog.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired

    private IUserDao userDao;
    @Override
    public User checkUser(String username, String password) {

        User user = userDao.findUserByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
