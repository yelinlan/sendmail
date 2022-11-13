package com.yll.servlet;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.SneakyThrows;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 *@项目名称: sendmail
 *@类名称: SendMail
 *@类描述:
 *@创建人: yll
 *@创建时间: 2022/11/13 18:59
 **/
public class SendMail extends Thread {

	private User user;

	public SendMail(User user) {
		this.user = user;
	}

	@Override
	public void run() {
		try {
			Properties prop = new Properties();
			this.getClass().getClassLoader().getResourceAsStream("mail.properties");
			prop.setProperty("mail.host", "smtp.qq.com");//设置邮件服务器
			prop.setProperty("mail.transport.protocol", "smtp");        // 邮件发送协议
			prop.setProperty("mail.smtp.auth", "true");        // 需要验证用户名密码
			//关于邮航，还要设置SSL加密，加上以下代码即可，大厂
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			prop.put("mail.smtp.ssl.enable", "true");
			prop.put("mail.smtp.ssl.socketFactory", sf);

			//使用JavaMail发送邮件的5个步骤
			//1、创建定义整个应用程序所需的环境信息的Session对象//创建定义整个应用程序所需的环境信息的Session对象/ /QQ才有!其他邮箱就不用
			Session session = Session.getDefaultInstance(prop, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					//发件人邮件用户名、授权码
					return new PasswordAuthentication(user.getMail(), user.getPassword());
				}
			});
			session.setDebug(true);
			//2、通过session符到transport对象
			Transport ts = session.getTransport();
			//3、使用邮箱的用户名和授权码连上邮件服务嚣
			ts.connect("smtp.qq.com", user.getMail(), user.getPassword());
			//4、创建邮件 I
			//注意需要传递Session ;
			MimeMessage message = new MimeMessage(session);
			//指明邮件的发件人
			message.setFrom(new InternetAddress(user.getMail()));
			//指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getMail()));//邮件的标题
			message.setSubject("********" + user.getUsername() + "注册成功通知*********");//邮件的文本内容

			//准备图（数擦
			MimeBodyPart image = new MimeBodyPart();
			DataHandler dh = new DataHandler(new FileDataSource("C:\\Users\\Administrator\\Desktop\\心海.png"));
			image.setDataHandler(dh);
			image.setContentID("img1");
			//准备正文数据
			MimeBodyPart text = new MimeBodyPart();
			text.setContent("<h1 style='color:red'>穿越时空的思念！！!</h1><br>这是一封邮件正文带图片<img src='cid:img1'>的邮件",
					"text/html;charset=UTF-8");


			//描述数据关系
			MimeMultipart mm = new MimeMultipart();
			mm.addBodyPart(text);
			mm.addBodyPart(image);
			mm.setSubType("related");

			//设理到消息中,保存修改
			message.setContent(mm);
			message.saveChanges();


			//5、发送邮件
			ts.sendMessage(message, message.getAllRecipients());
			ts.close();
		} catch (GeneralSecurityException | MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}