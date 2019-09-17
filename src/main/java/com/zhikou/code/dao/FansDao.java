package com.zhikou.code.dao;

import com.zhikou.code.bean.Fans;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface FansDao extends Mapper<Fans> {

    int getFansCount(int userId);

    int getConcernCount(int userId);

    List<Map<String,Object>> getFansList(int userId);

    List<Map<String,Object>> getConcernList(int userId);

}
