package com.zhikou.code.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageParam {

    private String title;

    private String content;

    private String province;

    private String city;

    private String district;

    private String address;

    private int adcode;

    private String classify;//分类

    private int rebate;

    private String filePath;

    private Date startTime;

    private int pageNum;

    private int pageSize;

    private int messageId;

    private int type;

    private Date endTime;

    private Date warnTime;

    private String inputText;

    private String rebateOrder;

    private double lon;

    private double lat;

    private int radius;
}
