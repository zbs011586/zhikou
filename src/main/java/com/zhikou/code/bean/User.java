package com.zhikou.code.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
/**
 * @description 用户表
 * @author 张宝帅
 * @date 2019/9/1 0:08
 */
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date register_time;
    //最后登录时间
    @Column(name = "last_login_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date last_login_time;
    //最后登录Ip
    @Column(name = "last_login_ip")
    private String last_login_ip;
    //用户角色 0=普通用户 1=商家
    @Column(name = "user_role")
    private Integer user_role;
    //昵称
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
