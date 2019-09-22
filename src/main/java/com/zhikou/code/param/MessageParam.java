package com.zhikou.code.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageParam {

    private String title;

    private String content;

    private int rebate;

    private String filePath;

    private Date startTime;

    private int pageNum;

    private int pageSize;

    private int messageId;

    private int type;

    private Date endTime;

    private Date warnTime;

}
