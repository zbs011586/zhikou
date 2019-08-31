package com.zhikou.code.dao;

import com.zhikou.code.bean.Token;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description 用户Token
 * @author 张宝帅
 * @date 2019/8/25 20:52
 */
public interface ApiTokenDao extends JpaRepository<Token,Integer> {

    Token queryByUserId(Long userId);

    Token queryByToken(String token);

}
