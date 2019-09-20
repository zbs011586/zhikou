package com.zhikou.code.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhikou.code.bean.*;
import com.zhikou.code.commons.HttpResponse;
import com.zhikou.code.dao.*;
import com.zhikou.code.param.MessageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserLikeDao userLikeDao;

    @Autowired
    private UserCommentDao userCommentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private UserScanDao userScanDao;

    public HttpResponse myScan(int userId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messages = messageDao.myScanMessage(userId);
        List<Message> myLikes = handleMessage(messages,userId);
        return HttpResponse.OK(new PageInfo(myLikes));
    }

    public HttpResponse myLike(int userId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messages = messageDao.myLikeMessage(userId);
        List<Message> myLikes = handleMessage(messages,userId);
        return HttpResponse.OK(new PageInfo(myLikes));
    }

    public HttpResponse likeUser(int messageId){
        UserLike userLike = new UserLike();
        userLike.setMessageId(messageId);
        List<UserLike> likes = userLikeDao.select(userLike);
        return HttpResponse.OK(likes);
    }

    public HttpResponse commentUser(int userId,int messageId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<UserComment> comments = userCommentDao.commentUser(messageId);
        //监控此接口入库 用户浏览记录
        saveUserScan(userId,messageId);
        return HttpResponse.OK(new PageInfo(comments));

    }
    public HttpResponse saveComment(int messageId,int userId,String content){
        User user = getUser(userId);
        UserComment userComment = new UserComment(messageId, userId,user.getUsername(), content, new Date());
        userCommentDao.insert(userComment);
        return HttpResponse.OK("评论成功");
    }

    /**
     * @description 用户对消息的点赞 或者取消 type=0 点赞 type=1 取消
     * @author 张宝帅
     * @date 2019/9/19 21:32
     */
    public HttpResponse saveLike(int messageId,int userId,int type){
        User user = getUser(userId);
        UserLike userLike = new UserLike();
        userLike.setMessageId(messageId);
        userLike.setUserId(userId);
        userLike.setUserName(user.getUsername());
        if (type == 1){
            userLikeDao.delete(userLike);
        }else {
            userLike.setUpdateTime(new Date());
            userLikeDao.insert(userLike);
        }
        return HttpResponse.OK("操作成功");
    }

    public HttpResponse newMessage(int userId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messages = messageDao.newMessage();
        List<Message> newMessage = handleMessage(messages,userId);
        return HttpResponse.OK(new PageInfo(newMessage));
    }

    public HttpResponse createMessage(MessageParam param,Integer userId){
        Message message = new Message();
        message.setUserId(userId);
        User user = getUser(userId);
        message.setUserName(user.getUsername());
        message.setAvatar(user.getAvatar());
        message.setTitle(param.getTitle());
        message.setContent(param.getContent());
        message.setRebate(param.getRebate());
        message.setStartTime(param.getStartTime());
        message.setFilePath(param.getFilePath());
        message.setCreateTime(new Date());
        messageDao.insert(message);

        return HttpResponse.OK("新的折扣消息发布成功");
    }

    /**
     * @description 根据userId获取user对象
     * @author 张宝帅
     * @date 2019/9/20 19:52
     */
    private User getUser(int userId){
        User user = new User();
        user.setUserId(userId);
        User one = userDao.selectOne(user);
        return one;
    }

    /**
     * @description 入库 用户浏览记录
     * @author 张宝帅
     * @date 2019/9/20 19:40
     */
    private void saveUserScan(int userId,int messageId){
        UserScan userScan = new UserScan();
        userScan.setUserId(userId);
        userScan.setMessageId(messageId);
        UserScan one = userScanDao.selectOne(userScan);
        if (one == null){
            //新消息入库
            User user = getUser(userId);
            userScan.setUserName(user.getUsername());
            userScan.setUpdateTime(new Date());
            userScanDao.insert(userScan);
        }else {
            userScanDao.flushUpdateTime(messageId,userId,new Date());
            //更新updateTime
        }
    }

    /**
     * @description 处理messages
     * @author 张宝帅
     * @date 2019/9/20 19:17
     */
    private List<Message> handleMessage(List<Message> messages,int userId){
        for (Message message : messages) {
            Map<String, Object> map = handleStatus(message.getMessageId(),userId);
            message.setShopStatus((Integer) map.get("shopStatus"));
            message.setLikeStatus((Integer) map.get("likeStatus"));
        }
        return messages;
    }

    /**
     * @description 处理当前用户 是否为商家 是否已点赞 是否已提醒 状态
     * @author 张宝帅
     * @date 2019/9/20 18:27
     */
    private Map<String,Object> handleStatus(int messageId,int userId){
        HashMap map = new HashMap();
        //处理商家状态
        Shop shop = new Shop();
        shop.setUserId(userId);
        Shop shopOne = shopDao.selectOne(shop);
        if (shopOne == null){
            map.put("shopStatus",0);//不是商家
        }else {
            map.put("shopStatus",1);//是商家
        }
        //处理点赞状态
        UserLike userLike = new UserLike();
        userLike.setMessageId(messageId);
        userLike.setUserId(userId);
        UserLike likeOne = userLikeDao.selectOne(userLike);
        if (likeOne == null){
            map.put("likeStatus",0);//未点赞
        }else {
            map.put("likeStatus",1);//已点赞
        }
        //处理提醒我状态
        return map;
    }
}
