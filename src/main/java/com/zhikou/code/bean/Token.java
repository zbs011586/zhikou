package com.zhikou.code.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * @description 用户token表
 * @author 张宝帅
 * @date 2019/8/25 21:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_token")
public class Token{

    //用户ID
    @Id
    @Column(name = "user_id")
    private Integer userId;
    //token
    @Column(name = "token")
    private String token;
    //过期时间
    @Column(name = "expire_time")
    private Date expireTime;
    //更新时间
    @Column(name = "update_time")
    private Date updateTime;

}
