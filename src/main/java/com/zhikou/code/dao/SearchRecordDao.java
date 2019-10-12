package com.zhikou.code.dao;

import com.zhikou.code.bean.SearchRecord;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SearchRecordDao extends Mapper<SearchRecord> {

    List<String> mySearch(@Param("userId")int userId);
    List<String> hotSearch();

}
