package com.zhikou.code.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserParam {

    private int pageNum;

    private int pageSize;

    private int goalUserId;

    private int myUserId;

    private String inputText;

    private String token;

}
