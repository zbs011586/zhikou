package com.zhikou.code.service;

import com.zhikou.code.bean.User;
import com.zhikou.code.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User queryByOpenId(String openId) {
        return userDao.queryByOpenId(openId);
    }

    public void save(User user) {
        userDao.save(user);
    }

    public void update(User user) {
        userDao.saveAndFlush(user);
    }

}
