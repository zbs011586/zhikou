package com.zhikou.code.service;

import com.zhikou.code.bean.Shop;
import com.zhikou.code.bean.User;
import com.zhikou.code.commons.HttpResponse;
import com.zhikou.code.dao.ShopDao;
import com.zhikou.code.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class AccountService {
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private UserDao userDao;


    public HttpResponse getUserInfo(int userId){
        User user = userDao.queryByUserId(userId);
        HashMap map = new HashMap();
        if (user.getUser_role() == 0){
            map.put("role",0);
            map.put("info",user);
        }else {
            Shop shop = shopDao.queryByUserId(userId);
            map.put("role",1);
            map.put("info",shop);
        }
        return HttpResponse.OK(map);
    }

    public HttpResponse shopRegistry(Shop param){
        param.setCreateTime(new Date());
        param.setUpdateTime(new Date());
        Shop shop = shopDao.save(param);
        //修改user表中user_role状态为1
        userDao.updateRoleStatus(param.getUserId());
        return HttpResponse.OK(shop);
    }
}
