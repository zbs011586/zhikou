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

    private double rebate;

    private String filePath;

    private Date startTime;

    private int pageNum;

    private int pageSize;

    private int messageId;

    private int type;

    private Date endTime;

    private Date warnTime;

    private String inputText;

    private int rebateOrder;

    private double lon;

    private double lat;

    private int radius;

    private int myUserId;

    private String msgType;

    private double fullAmount;

    private double lessAmount;

    private String buyWares;

    private double buyAmount;

    private String giftWares;

    private double giftAmount;
}
