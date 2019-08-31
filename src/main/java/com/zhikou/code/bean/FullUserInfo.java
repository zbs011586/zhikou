package com.zhikou.code.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * @description 用户登录完整的信息
 * @author 张宝帅
 * @date 2019/9/1 0:10
 */
public class FullUserInfo {

    //errMsg
    private String errMsg;
    //rawData
    private String rawData;
    //userInfo
    private UserInfo userInfo;
    //encryptedData
    private String encryptedData;
    //iv
    private String iv;
    //signature
    private String signature;

}
