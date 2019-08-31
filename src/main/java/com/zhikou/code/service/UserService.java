package com.zhikou.code.service;

import com.zhikou.code.bean.UserVo;
import com.zhikou.code.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public UserVo queryByOpenId(String openId) {
        return userDao.queryByOpenId(openId);
    }

    public void save(UserVo userVo) {
        userDao.save(userVo);
    }

    public void update(UserVo user) {
        userDao.saveAndFlush(user);
    }

}
