package com.zhikou.code.dao;


import com.zhikou.code.bean.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @description 用户
 * @author 张宝帅
 * @date 2019/8/25 21:21
 */
public interface UserDao extends Mapper<User> {

    void updateRole(@Param("userId") int userId,@Param("role")int role);
}
