package com.yll.servlet;

import lombok.Data;

/**
 *@项目名称: sendmail
 *@类名称: User
 *@类描述:
 *@创建人: yll
 *@创建时间: 2022/11/13 18:38
 **/
@Data
public class User {

	private String username;
	private String password;
	private String mail;

}