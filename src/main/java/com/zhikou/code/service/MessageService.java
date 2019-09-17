package com.zhikou.code.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhikou.code.bean.Message;
import com.zhikou.code.commons.HttpResponse;
import com.zhikou.code.dao.MessageDao;
import com.zhikou.code.param.MessageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public HttpResponse showMessage(int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messages = messageDao.showMessage();
        return HttpResponse.OK(new PageInfo(messages));
    }

    public HttpResponse createMessage(MessageParam param,Integer userId){
        Message message = new Message();
        message.setUserId(userId);
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
