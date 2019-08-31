package com.zhikou.code.dao;

import com.zhikou.code.bean.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenDao extends JpaRepository<Token,Integer> {

    @Query(value = "select * from t_token where user_id=?1",nativeQuery = true)
    Token queryByUserId(Integer userId);

    @Query(value = "select * from t_token where token=?1",nativeQuery = true)
    Token queryByToken(String token);

}
