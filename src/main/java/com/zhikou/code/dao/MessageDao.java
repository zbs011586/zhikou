package com.zhikou.code.dao;

import com.zhikou.code.bean.Message;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface MessageDao extends Mapper<Message> {

    List<Message> showMessage();
}
