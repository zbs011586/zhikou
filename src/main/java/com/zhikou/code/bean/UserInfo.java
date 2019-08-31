package com.zhikou.code.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;


    private String avatarUrl;

    private String city;

    private Integer gender;

    private String nickName;

    private String province;

    public String getAvatarUrl() {
        return avatarUrl;
    }

}
