package com.zhikou.code.service;

import com.zhikou.code.bean.Fans;
import com.zhikou.code.bean.User;
import com.zhikou.code.commons.Constants;
import com.zhikou.code.commons.HttpResponse;
import com.zhikou.code.dao.FansDao;
import com.zhikou.code.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FansService {

    @Autowired
    private FansDao fansDao;

    @Autowired
    private UserDao userDao;

    public HttpResponse isFans(int concernUserUd,int fansUserId){
        Fans param = new Fans();
        param.setConcernUserId(concernUserUd);
        param.setFansUserId(fansUserId);
        Fans fans = fansDao.selectOne(param);
        HashMap map = new HashMap();
        //status=0 未关注 status=1已关注
        if (fans == null){
            map.put("status",0);
        }else {
            map.put("status",1);
        }
        return HttpResponse.OK(map);
    }

    public HttpResponse getConcernAndFansCount (int userId){
        int concernCount = fansDao.getConcernCount(userId);
        int fansCount = fansDao.getFansCount(userId);
        HashMap map = new HashMap();
        map.put("concernCount",concernCount);
        map.put("fansCount",fansCount);
        return HttpResponse.OK(map);
    }

    public HttpResponse getConcernList(int userId){
        List<Map<String, Object>> concernList = fansDao.getConcernList(userId);
        return HttpResponse.OK(concernList);
    }

    public HttpResponse getFansList(int userId){
        List<Map<String, Object>> fansList = fansDao.getFansList(userId);
        return HttpResponse.OK(fansList);
    }

    public HttpResponse saveConcern(int concernUserUd,int fansUserId){
        Fans param = new Fans();
        param.setConcernUserId(concernUserUd);
        param.setFansUserId(fansUserId);
        Fans query = fansDao.selectOne(param);
        if (query!=null){
            return HttpResponse.ERROR(Constants.ErrorCode.REQUEST_ERROR,"不能重复关注");
        }
        User user = new User();
        user.setUserId(concernUserUd);
        User concernUser = userDao.selectOne(user);
        user.setUserId(fansUserId);
        User fansUser = userDao.selectOne(user);
        Fans fans = new Fans(concernUserUd, concernUser.getNickname(), concernUser.getAvatar(), fansUserId, fansUser.getNickname(), fansUser.getAvatar(), new Date());
        fansDao.insert(fans);
        return HttpResponse.OK("关注成功");
    }

    public HttpResponse delConcern(int concernUserUd,int fansUserId){
        Fans fans = new Fans();
        fans.setConcernUserId(concernUserUd);
        fans.setFansUserId(fansUserId);
        fansDao.delete(fans);
        return HttpResponse.OK("取消关注成功");
    }
}
