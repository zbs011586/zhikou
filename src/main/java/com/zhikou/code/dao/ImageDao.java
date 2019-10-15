package com.zhikou.code.dao;

import com.zhikou.code.bean.Image;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ImageDao extends Mapper<Image> {

    List<Image> getImage();

}
