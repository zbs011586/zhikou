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
        Fans fans = fansDao.queryByConcernUserIdAndFansUserId(concernUserUd, fansUserId);
        HashMap map = new HashMap();
        //已关注status=0 未关注status=1
        if (fans == null){
            map.put("status",1);
        }else {
            map.put("status",0);
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
        Fans query = fansDao.queryByConcernUserIdAndFansUserId(concernUserUd, fansUserId);
        if (query!=null){
            return HttpResponse.ERROR(Constants.ErrorCode.REQUEST_ERROR,"不能重复关注");
        }
        User concernUser = userDao.queryByUserId(concernUserUd);
        User fansUser = userDao.queryByUserId(fansUserId);
        Fans fans = new Fans(concernUserUd, concernUser.getNickname(), concernUser.getAvatar(), fansUserId, fansUser.getNickname(), fansUser.getAvatar(), new Date());
        fansDao.save(fans);
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
