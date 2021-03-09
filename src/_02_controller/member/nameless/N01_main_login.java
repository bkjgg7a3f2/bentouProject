package _02_controller.member.nameless;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import _00.verifycode.CaptchaUtil;
//import _00.cookieProcess.BenTouCookieAction;
import _01_model._01_customer.Customer;
import _01_model._01_customer.CustomerService;
import _01_model._02_supply.Supply;
import _01_model._02_supply.SupplyService;

@Controller
@RequestMapping(value = "/welcome")
public class N01_main_login {
	@Autowired
	private HttpServletRequest request;

	private CustomerService cservice;
	private SupplyService suservice;

	@Autowired
	public N01_main_login(CustomerService cservice, SupplyService suservice) {
		this.cservice = cservice;
		this.suservice = suservice;
	}

	// 首頁(純轉移)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	private String home() {
		HttpSession session = request.getSession();
		session.invalidate();
		return "00MainPage/WelcomeLogin";
	}

	// 登入畫面(純轉移)
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String view_login() {
		HttpSession session = request.getSession();
		session.invalidate();
		return "01MemberAction/LoginPage";
	}

	// 登入(成功時導入選定身分之首頁)
	@RequestMapping(value = "/login_fail", method = RequestMethod.POST)
	public String login_check(@RequestParam(name = "useracc") String useracc,
			@RequestParam(name = "userpwd") String userpwd, @RequestParam(name = "memberType") String memberType,
			@RequestParam(name = "auto", required = false) String check, 
			@RequestParam(name = "verifycode") String verifycode) {
		HttpSession session = request.getSession();
		String code = (String) session.getAttribute("code");
		if (useracc == null || useracc.length() == 0 || userpwd == null || userpwd.length() == 0) {
			request.setAttribute("msgError", "請輸入帳號及密碼");
			return "01MemberAction/LoginPage";
		}

		// 取得id值
		int id = 0;
		if (memberType.equals("customer")) {
			Customer cus = cservice.selectOnebyacc_pwd(new Customer(useracc, userpwd));
			if (cus != null) {
				if (cus.getCstmr_vip().equals("member") || cus.getCstmr_vip().equals("vip")) {
					id = cus.getCstmr_id();
				} else if (cus.getCstmr_vip().equals("unverified") || cus.getCstmr_vip().equals("banned")) {
					request.setAttribute("msgError", "此帳號尚未啟用或是已停權");
				} else {
					id = cus.getCstmr_id();
				}
			} else {
				request.setAttribute("msgError", "帳號或密碼錯誤，請重新輸入");
			}
		} else if (memberType.equals("shop")) {
			Supply sup = suservice.selectOnebyacc_pwd(new Supply(useracc, userpwd));
			if (sup != null) {
				if (sup.getSupply_vip().equals("member") || sup.getSupply_vip().equals("vip")) {
					id = sup.getSupply_id();
				} else if (sup.getSupply_vip().equals("unverified") || sup.getSupply_vip().equals("banned")) {
					request.setAttribute("msgError", "此帳號尚未啟用或是已停權");
				} else {
					id = sup.getSupply_id();
				}
			} else {
				request.setAttribute("msgError", "帳號或密碼錯誤，請重新輸入");
			}
		}

		if (id == 0) {
			return "01MemberAction/LoginPage";
		}

		if (verifycode == null) {
			request.setAttribute("msgError", "別重複提交");
			return "01MemberAction/LoginPage";
		}

		if (!verifycode.equalsIgnoreCase(code)) {
			request.setAttribute("msgError", "輸入驗證碼錯誤");
			return "01MemberAction/LoginPage";
		}

		// 保持登入(寫cookie)
		if (check != null && check.equals("true")) {
//			BenTouCookieAction cookie = new BenTouCookieAction(request, response);
//			cookie.write(memberType, id);
		}

		// 身分驗證
		// 設定登入狀態,身分,id
		if (memberType.equals("customer")) {
			session.setAttribute("bentou_loginstatus", "true");
			session.setAttribute("memberType", memberType);
			session.setAttribute("cstmr_id", id);
			return "redirect: " + request.getContextPath() + "/cstmr/";
		} else if (memberType.equals("shop")) {
			session.setAttribute("bentou_loginstatus", "true");
			session.setAttribute("memberType", memberType);
			session.setAttribute("supply_id", id);
			return "redirect: " + request.getContextPath() + "/shop/";
		}

		request.setAttribute("msgError", "已舉報不當行為");
		return "00MainPage/WarningError";
	}

	// 驗證碼
	@RequestMapping(value = "createCode", method = RequestMethod.GET)
	public void createCode(HttpServletResponse response) throws IOException {
		// 通知瀏覽器不要快取
		response.setHeader("Expires", "-1");// 控制快取的失效日期
		response.setHeader("Cache-Control", "no-cache");// 必須先與伺服器確認返回的響應是否被更改，然後才能使用該響應來滿足後續對同一個網址的請求
		response.setHeader("Pragma", "-1");
		CaptchaUtil util = new CaptchaUtil();
		// 將驗證碼輸入到session中，用來驗證
		String code = util.getString();
		request.getSession().setAttribute("code", code);
		
		// 輸出到web頁面
		ImageIO.write(util.getImage(), "jpg", response.getOutputStream());
	}
}
