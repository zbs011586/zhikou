package com.zhikou.code.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * @description 登录用户的基础info信息
 * @author 张宝帅
 * @date 2019/9/1 2:32
 */
public class UserInfo {

    private String avatarUrl;

    private String city;

    private Integer gender;

    private String nickName;

    private String province;

}
