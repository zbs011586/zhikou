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



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long userId;
    //会员名称
    @Column(name = "username")
    private String username;
    //会员密码
    @Column(name = "password")
    private String password;
    //性别
    @Column(name = "gender")
    private Integer gender;
    //出生日期
    @Column(name = "birthday")
    private Date birthday;
    //注册时间
    @Column(name = "register_time")
    private Date register_time;
    //最后登录时间
    @Column(name = "last_login_time")
    private Date last_login_time;
    //最后登录Ip
    @Column(name = "last_login_ip")
    private String last_login_ip;
    //会员等级
    @Column(name = "user_level_id")
    private Integer user_level_id;
    //别名
    @Column(name = "nickname")
    private String nickname;
    //手机号码
    @Column(name = "mobile")
    private String mobile;
    //注册Ip
    @Column(name = "register_ip")
    private String register_ip;
    //头像
    @Column(name = "avatar")
    private String avatar;
    //微信Id
    @Column(name = "weixin_openid")
    private String weixin_openid;

}
