package com.zhikou.code.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @description 促销活动信息表
 * @author 张宝帅
 * @date 2019/9/16 15:48
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "t_message")
public class Message {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer messageId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "rebate")
    private Integer rebate;//折扣大小

    @Column(name = "file_path")
    private String filePath;//上传文件路径

    @Column(name = "create_time")
    private Date createTime;//创建时间

    @Column(name = "start_time")
    private Date startTime;//活动开始时间
}
