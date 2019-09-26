package com.zhikou.code.dao;

import com.zhikou.code.bean.Message;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;


public interface MessageDao extends Mapper<Message> {

    List<Message> newMessage(@Param("adcode")int adcode);

    List<Message> myLikeMessage(@Param("userId") int userId);

    List<Message> myScanMessage(@Param("userId") int userId);

    List<Message> myWarnMessage(@Param("userId") int userId);

    List<Message> warnInfo(@Param("warnTime") Date warnTime);

    List<Integer> threeCount(@Param("messageId")int messageId);
}
