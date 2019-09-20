package com.zhikou.code.dao;

import com.zhikou.code.bean.UserComment;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserCommentDao extends Mapper<UserComment> {

    List<UserComment> commentUser(int messageId);

}
