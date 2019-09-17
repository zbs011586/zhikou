package com.zhikou.code.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "t_fans")
/**
 * @description 用户的关注/粉丝 关系表
 * @author 张宝帅
 * @date 2019/9/2 18:28
 */
public class Fans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concern_user_id")
    private Integer concernUserId;

    @Column(name = "concern_nickname")
    private String concernNickname;

    @Column(name = "concern_avatar")
    private String concernAvatar;

    @Column(name = "fans_user_id")
    private Integer fansUserId;

    @Column(name = "fans_nickname")
    private String fansNickname;

    @Column(name = "fans_avatar")
    private String fansAvatar;

    @Column(name = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

}
