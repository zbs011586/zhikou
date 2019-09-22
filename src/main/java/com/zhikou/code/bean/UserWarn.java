package com.zhikou.code.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "t_user_warn")
public class UserWarn {

    private Integer messageId;

    private Integer userId;

    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date warnTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

}
