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
@Table(name = "t_shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopId;

    private String shopName;

    private String shopPhoto;

    private Integer userId;

    private String province;

    private String city;

    private String district;

    private Integer adcode;

    private String address;

    @Transient
    private double distance;

    private Double lon;

    protected Double lat;

    private String classify;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @Transient
    private String userName;

}
