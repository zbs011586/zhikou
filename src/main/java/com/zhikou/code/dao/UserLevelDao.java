package com.zhikou.code.dao;


import com.zhikou.code.bean.UserLevelVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description 用户类型
 * @author 张宝帅
 * @date 2019/8/25 21:22
 */
@Repository
public interface UserLevelDao extends JpaRepository<UserLevelVo,Integer> {
	
}
