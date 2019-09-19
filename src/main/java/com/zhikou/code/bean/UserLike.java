package com.zhikou.code.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

/**
 * @description 用户对消息的点赞记录表
 * @author 张宝帅
 * @date 2019/9/19 21:26
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "t_user_like")
public class UserLike {

    private Integer messageId;

    private Integer userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

}
