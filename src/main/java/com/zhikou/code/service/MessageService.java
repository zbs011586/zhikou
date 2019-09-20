package com.zhikou.code.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhikou.code.bean.Message;
import com.zhikou.code.bean.User;
import com.zhikou.code.bean.UserComment;
import com.zhikou.code.bean.UserLike;
import com.zhikou.code.commons.HttpResponse;
import com.zhikou.code.dao.MessageDao;
import com.zhikou.code.dao.UserCommentDao;
import com.zhikou.code.dao.UserDao;
import com.zhikou.code.dao.UserLikeDao;
import com.zhikou.code.param.MessageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    public HttpResponse likeUser(int messageId){
        UserLike userLike = new UserLike();
        userLike.setMessageId(messageId);
        List<UserLike> likes = userLikeDao.select(userLike);
        return HttpResponse.OK(likes);
    }

    public HttpResponse commentUser(int messageId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        UserComment userComment = new UserComment();
        userComment.setMessageId(messageId);
        List<UserComment> comments = userCommentDao.select(userComment);
        return HttpResponse.OK(new PageInfo(comments));

    }
    public HttpResponse saveComment(int messageId,int userId,String content){
        User user = new User();
        user.setUserId(userId);
        User one = userDao.selectOne(user);
        UserComment userComment = new UserComment(messageId, userId,one.getUsername(), content, new Date());
        userCommentDao.insert(userComment);
        return HttpResponse.OK("评论成功");
    }

    /**
     * @description 用户对消息的点赞 或者取消 type=0 点赞 type=1 取消
     * @author 张宝帅
     * @date 2019/9/19 21:32
     */
    public HttpResponse saveLike(int messageId,int userId,int type){
        User user = new User();
        user.setUserId(userId);
        User one = userDao.selectOne(user);
        UserLike userLike = new UserLike();
        userLike.setMessageId(messageId);
        userLike.setUserId(userId);
        userLike.setUserName(one.getUsername());
        if (type == 1){
            userLikeDao.delete(userLike);
        }else {
            userLike.setCreateTime(new Date());
            userLikeDao.insert(userLike);
        }
        return HttpResponse.OK("操作成功");
    }

    public HttpResponse showMessage(int userId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messages = messageDao.showMessage();
        for (Message message : messages) {
            UserLike userLike = new UserLike();
            userLike.setMessageId(message.getMessageId());
            userLike.setUserId(userId);
            UserLike one = userLikeDao.selectOne(userLike);
            if (one == null){
                message.setLikeStatus(0);//未点赞
            }else {
                message.setLikeStatus(1);//已点赞
            }
        }
        return HttpResponse.OK(new PageInfo(messages));
    }

    public HttpResponse createMessage(MessageParam param,Integer userId){
        Message message = new Message();
        message.setUserId(userId);
        User user = new User();
        user.setUserId(userId);
        User one = userDao.selectOne(user);
        message.setUserName(one.getUsername());
        message.setAvatar(one.getAvatar());
        message.setTitle(param.getTitle());
        message.setContent(param.getContent());
        message.setRebate(param.getRebate());
        message.setStartTime(param.getStartTime());
        message.setFilePath(param.getFilePath());
        message.setCreateTime(new Date());
        messageDao.insert(message);

        return HttpResponse.OK("新的折扣消息发布成功");
    }

}
