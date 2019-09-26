package com.zhikou.code.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FansParam {

    private int type;//0=取消关注 1=关注

    private int concernUserId;
}
