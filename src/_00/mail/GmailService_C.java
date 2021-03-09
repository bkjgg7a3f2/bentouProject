package _00.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import _00.mail.Interface.EmailService_C;
import _01_model._01_customer.Customer;

public class GmailService_C implements EmailService_C {
	private final Properties props = new Properties();
	private final String mailUser;
	private final String mailPassword;

	public GmailService_C(String mailUser, String mailPassword) {
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", 587);
		this.mailUser = mailUser;
		this.mailPassword = mailPassword;
	}

	@Override
	public void validationLink(Customer cus) throws AddressException, MessagingException, IOException {
		String link = String.format("http://localhost:8080/Presentation/welcome/verify?acc=%s&email=%s&mem=cus",
				cus.getCstmr_acc(), cus.getCstmr_email());

		String anchor = String.format("<a href='%s'>驗證郵件</a>", link);

		String html = String.format("請按 %s 啟用帳戶或複製鏈結至網址列：<br><br> %s", anchor, link);

		javax.mail.Message message = createMessage(mailUser, cus.getCstmr_email(), "註冊結果", html);
		Transport.send(message);
	}

	@Override
	public void failedRegistration(String custName, String custEmail)
			throws AddressException, MessagingException, IOException {
			javax.mail.Message message = createMessage(mailUser, custEmail, "註冊結果",
					String.format("帳戶申請失敗，使用者名稱 %s 或郵件 %s 已存在！", custName, custEmail));
			Transport.send(message);
	}

	private javax.mail.Message createMessage(String from, String to, String subject, String text)
			throws MessagingException, AddressException, IOException {
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailUser, mailPassword);
			}
		});

		Multipart multiPart = multiPart(text);
		javax.mail.Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		message.setSentDate(new Date());
		message.setContent(multiPart);
		return message;
	}

	private Multipart multiPart(String text) throws MessagingException, UnsupportedEncodingException, IOException {
		MimeBodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(text, "text/html;charset=UTF-8");
		Multipart multiPart = new MimeMultipart();
		multiPart.addBodyPart(htmlPart);
		return multiPart;
	}

	@Override
	public void passwordResetLink(Customer cus) throws AddressException, MessagingException, IOException {
			String link = String.format("http://localhost:8080/Presentation/welcome/reset_password?acc=%s&email=%s&mem=cus",
					cus.getCstmr_acc(), cus.getCstmr_email());

			String anchor = String.format("<a href='%s'>重設密碼</a>", link);

			String html = String.format("請按 %s 或複製鏈結至網址列：<br><br> %s", anchor, link);

			javax.mail.Message message = createMessage(mailUser, cus.getCstmr_email(), "重設密碼", html);
			Transport.send(message);
	}
}
