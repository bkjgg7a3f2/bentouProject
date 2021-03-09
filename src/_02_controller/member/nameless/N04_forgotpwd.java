package _02_controller.member.nameless;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import _00.mail.GmailService_C;
import _00.mail.GmailService_S;
import _01_model._01_customer.Customer;
import _01_model._01_customer.CustomerService;
import _01_model._02_supply.Supply;
import _01_model._02_supply.SupplyService;

@Controller
@PropertySource("classpath:mailuser.properties")
@RequestMapping(value = "/welcome")
public class N04_forgotpwd {
	@Autowired
	private HttpServletRequest request;

	private CustomerService cservice;
	private SupplyService suservice;

	@Autowired
	public N04_forgotpwd(CustomerService cservice, SupplyService suservice) {
		this.cservice = cservice;
		this.suservice = suservice;
	}

	@Autowired
	private Environment env;

	// 忘記密碼頁面(純轉移)
	@RequestMapping(value = "/forgetpwd", method = RequestMethod.GET)
	public String forgetpwd() {
		return "01MemberAction/ForgetPwdPage";
	}

	// 寄信件(忘記密碼)
	@RequestMapping(value = "/forgetpwd_fail", method = RequestMethod.POST)
	public String forgetpwd_mail(@RequestParam(name = "acc") String acc, @RequestParam(name = "email") String email,
			@RequestParam(name = "memberType") String memberType)
			throws AddressException, MessagingException, IOException {
		String mailUser = env.getRequiredProperty("mailUser");
		String mailPassword = env.getRequiredProperty("mailPassword");

		boolean check_acc = false;
		if (memberType.equals("customer")) {
			Customer cus = cservice.selectOnebyacc(acc);
			if (cus.getCstmr_email().equals(email)) {
				GmailService_C gmailservice = new GmailService_C(mailUser, mailPassword);
				gmailservice.passwordResetLink(cus);
				check_acc = true;
			}
		} else if (memberType.equals("shop")) {
			Supply sup = suservice.selectOnebyacc(acc);
			if (sup.getSupply_email().equals(email)) {
				GmailService_S gmailservice = new GmailService_S(mailUser, mailPassword);
				gmailservice.passwordResetLink(sup);
				check_acc = true;
			}
		}

		if (!check_acc) {
			return "";//刻意失敗
		}

		request.setAttribute("email", email);
		return "01MemberAction/ForgetPwdPage_ajax";
	}

	// 重設密碼頁面(透過信件)
	@RequestMapping(value = "/reset_password", method = RequestMethod.GET)
	public String show_reset_password() throws IOException {
		String acc = request.getParameter("acc");
		String email = request.getParameter("email");
		String memberType = request.getParameter("mem");

		if (memberType.equals("cus")) {
			Customer cus = cservice.selectOnebyacc(acc);
			if (cus.getCstmr_email().equals(email)) {
				request.setAttribute("display_S", "none");
				request.setAttribute("memberType", "customer");// hidden
				request.setAttribute("acc", acc);// hidden
				request.setAttribute("username", cus.getCstmr_name());
				return "01MemberAction/ResetPwdPage";
			}
		} else if (memberType.equals("sup")) {
			Supply sup = suservice.selectOnebyacc(acc);
			if (sup.getSupply_email().equals(email)) {
				request.setAttribute("display_C", "none");
				request.setAttribute("memberType", "shop");// hidden
				request.setAttribute("acc", acc);// hidden
				request.setAttribute("username", sup.getSupply_name());
				return "01MemberAction/ResetPwdPage";
			}
		}

		request.setAttribute("warning", "已舉報不正當行為");
		return "00MainPage/WarningError";
	}

	// 重設密碼 + 轉移成功頁面
	@RequestMapping(value = "/reset_password_fail", method = RequestMethod.POST)
	public String reset_password(@RequestParam(name = "memberType") String memberType,
			@RequestParam(name = "acc") String acc, @RequestParam(name = "pwd") String pwd,
			@RequestParam(name = "pwd2") String pwd2) {
		boolean check = true;
		if (pwd == null || pwd.length() == 0 || pwd2 == null || pwd2.length() == 0) {
			request.setAttribute("errors", "請輸入重設密碼並符合格式");
			check = false;
		}

		if (!pwd.equals(pwd2)) {
			request.setAttribute("errors", "驗證密碼錯誤，請重新輸入");
			check = false;
		}

		if (!check) {
			if (memberType.equals("customer")) {
				request.setAttribute("display_S", "none");
				request.setAttribute("memberType", memberType);// hidden
				request.setAttribute("acc", acc);// hidden
				request.setAttribute("username", cservice.selectOnebyacc(acc).getCstmr_name());
			} else if (memberType.equals("shop")) {
				request.setAttribute("display_C", "none");
				request.setAttribute("memberType", memberType);// hidden
				request.setAttribute("acc", acc);// hidden
				request.setAttribute("username", suservice.selectOnebyacc(acc).getSupply_name());
			}

			return "01MemberAction/ResetPwdPage";
		}

		if (memberType.equals("customer")) {
			Customer cus = cservice.selectOnebyacc(acc);
			cus.setCstmr_pwd(pwd);
		} else if (memberType.equals("shop")) {
			Supply sup = suservice.selectOnebyacc(acc);
			sup.setSupply_pwd(pwd);
		}

		return "redirect: " + request.getContextPath() + "/welcome/reset_password_success";
	}

	// 重設密碼成功(純轉移)
	@RequestMapping(value = "/reset_password_success", method = RequestMethod.GET)
	public String reset_success() {
		request.setAttribute("error", "密碼更改成功，請重新登入");
		return "00MainPage/WarningError";
	}

}
