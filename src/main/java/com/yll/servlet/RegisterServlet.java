package com.yll.servlet;

import com.yll.servlet.common.BaseServlet;
import com.yll.servlet.util.ConvertUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *@项目名称: sendmail
 *@类名称: RegisterServlet
 *@类描述:
 *@创建人: yll
 *@创建时间: 2022/11/13 18:36
 **/
public class RegisterServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			request = req;
			response = resp;
			User user = ConvertUtil.convertBean(User.class, req);
			System.out.println(user);
			SendMail sendMail = new SendMail(user);
			sendMail.start();
			req.setAttribute("msg", "邮件发送成功！请注意查收邮件！");
			dispatcher("/info.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}