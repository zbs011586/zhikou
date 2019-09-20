package com.zhikou.code.dao;

import com.zhikou.code.bean.UserScan;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface UserScanDao extends Mapper<UserScan> {

    void flushUpdateTime(@Param("messageId")int messageId, @Param("userId")int userId, @Param("date")Date date);

}
