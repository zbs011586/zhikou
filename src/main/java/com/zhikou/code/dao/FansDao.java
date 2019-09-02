package com.zhikou.code.dao;

import com.zhikou.code.bean.Fans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Map;

@Repository
public interface FansDao extends JpaRepository<Fans,Integer> {

    @Query(value = "select count(*) from t_fans where concern_user_id=?1",nativeQuery = true)
    int getFansCount(int userId);

    @Query(value = "select count(*) from t_fans where fans_user_id=?1",nativeQuery = true)
    int getConcernCount(int userId);

    @Query(value = "select fans_user_id as userId,fans_nickname as nickName,fans_avatar as avatar from t_fans where concern_user_id=?1",nativeQuery = true)
    List<Map<String,Object>> getFansList(int userId);

    @Query(value = "select concern_user_id as userId,concern_nickname as nickName,concern_avatar as avatar from t_fans where fans_user_id=?1",nativeQuery = true)
    List<Map<String,Object>> getConcernList(int userId);

    @Query(value = "select * from t_fans where concern_user_id=?1 and fans_user_id=?2",nativeQuery = true)
    Fans queryByConcernUserIdAndFansUserId(int concernUserId,int fansUserId);


}
