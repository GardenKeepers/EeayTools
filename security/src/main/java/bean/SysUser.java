package bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUser implements Serializable {

	private Integer id; //uuid

	private String account; //账户名

	private String name; //名字

	private String password; //密码

	private String phone; //手机号

	private Integer version; //描述

	private Integer state; //状态 1有效,2无效

}

