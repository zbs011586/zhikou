package com.zhikou.code.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "t_search_record")
public class SearchRecord {

    private Integer id;

    private Integer userId;

    private String inputText;

    private Date createTime;

}
