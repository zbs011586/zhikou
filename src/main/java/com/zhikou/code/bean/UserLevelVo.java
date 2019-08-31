package com.zhikou.code.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * @description 商城_用户级别
 * @author 张宝帅
 * @date 2019/8/25 21:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_level")
public class UserLevelVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	@Id
	@Column(name = "id")
	private Integer id;
	//名称
	@Column(name = "name")
	private String name;
	//描述
	@Column(name = "description")
	private String description;

}
