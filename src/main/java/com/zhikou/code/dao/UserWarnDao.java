package com.zhikou.code.dao;

import com.zhikou.code.bean.UserWarn;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface UserWarnDao extends Mapper<UserWarn> {

    List<Date> myWarn(@Param("userId")int userId, @Param("startTime")Date startTime,@Param("endTime")Date endTime);

}
