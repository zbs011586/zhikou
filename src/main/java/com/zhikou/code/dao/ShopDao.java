package com.zhikou.code.dao;

import com.zhikou.code.bean.Shop;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ShopDao extends Mapper<Shop> {

    List<Shop> selectByLonLat(@Param("minLon")double minLon,@Param("maxLon")double maxLon,@Param("minLat")double minLat,@Param("maxLat")double maxLat);
}
