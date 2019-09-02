package com.zhikou.code.dao;

import com.zhikou.code.bean.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopDao extends JpaRepository<Shop,Integer> {

    @Query(value = "select * from t_shop where user_id=?1",nativeQuery = true)
    Shop queryByUserId(int userId);

}
