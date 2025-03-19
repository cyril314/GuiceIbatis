package com.fit.service;

import ninja.postoffice.Mail;
import ninja.postoffice.Postoffice;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @AUTO 邮件服务
 * @FILE MailService.java
 * @DATE 2017-10-17 下午3:08:45
 * @Author AIM
 */
public class MailService {

	@Inject
	Provider<Mail> mailProvider;

	@Inject
	Postoffice postoffice;

	/**
	 * 基础发送邮件
	 * 
	 * @param subject
	 *            主题
	 * @param setfrom
	 *            发件人
	 * @param addTo
	 *            收件人
	 * @param isHtml
	 *            是否网页
	 * @param body
	 *            发送内容
	 */
	public boolean sendMail(String subject, String setfrom, String addTo, boolean isHtml, String body) {
		return sendMail(subject, setfrom, addTo, false, null, isHtml, body, false, null, false);
	}

	/**
	 * 发送邮件
	 * 
	 * @param subject
	 *            主题
	 * @param setfrom
	 *            发件人
	 * @param addTo
	 *            收件人
	 * @param isReplyTo
	 * @param addReplyTo
	 * @param isHtml
	 *            是否网页
	 * @param body
	 *            发送内容
	 * @param isCc
	 *            是否添加抄送人
	 * @param addCc
	 *            抄送人
	 * @param isBcc
	 *            是否暗抄送
	 * @param addBcc
	 *            抄送人
	 */
	public boolean sendMail(String subject, String setfrom, String addTo, boolean isReplyTo, String addReplyTo, boolean isHtml, String body,
			boolean isCc, String[] addCc, boolean isBcc) {
		Mail mail = mailProvider.get();
		// fill the mail with content:
		// Set Subject: 头部头字段
		mail.setSubject(subject);
		// 发件人电子邮箱
		mail.setFrom(setfrom);
		if (isReplyTo) {
			mail.addReplyTo(addReplyTo);// 回复电子邮件
		}
		// 设置字符集
		mail.setCharset("utf-8");
		// 设置头部信息
		// mail.addHeader("header1", "value1");
		// 收件人电子邮箱
		mail.addTo(addTo);
		if (isCc) {
			for (String cc : addCc) {
				mail.addCc(cc);// 抄送电子邮箱
			}
		}
		if (isBcc) {
			for (String cc : addCc) {
				mail.addBcc(cc);// 暗抄送电子邮箱
			}
		}
		if (isHtml) {
			mail.setBodyHtml(body);
		} else {
			mail.setBodyText(body);
		}
		try {// finally send the mail
			postoffice.send(mail);
			return true;
		} catch (Exception e) {
			new RuntimeException("发送邮件失败", e);
		}
		return false;
	}
}
