package com.zhikou.code.dao;


import com.zhikou.code.bean.UserVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @description 用户
 * @author 张宝帅
 * @date 2019/8/25 21:21
 */
@Repository
public interface UserDao extends JpaRepository<UserVo,Integer> {

    @Query(value = "select * from t_user where weixin_openid=?1",nativeQuery = true)
    UserVo queryByOpenId(String openId);

}
