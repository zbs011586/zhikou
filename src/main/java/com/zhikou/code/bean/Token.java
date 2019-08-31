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
 * @description
 * @author 张宝帅
 * @date 2019/8/25 21:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户ID
    @Id
    @Column(name = "user_id")
    private Long userId;
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
