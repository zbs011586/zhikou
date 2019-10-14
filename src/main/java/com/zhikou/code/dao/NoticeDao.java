package com.zhikou.code.dao;

import com.zhikou.code.bean.Notice;
import tk.mybatis.mapper.common.Mapper;


public interface NoticeDao extends Mapper<Notice> {

    Notice getNotice();

}
