package _02_controller.member.nameless;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
public class N02_regist {
	@Autowired
	private HttpServletRequest request;

	private CustomerService cservice;
	private SupplyService suservice;

	@Autowired
	public N02_regist(CustomerService cservice, SupplyService suservice) {
		this.cservice = cservice;
		this.suservice = suservice;
	}

	@Autowired
	private Environment env;

	// 註冊前 提供服務頁面(純轉移)
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register_service() {
		return "01MemberAction/RegpageAccPwd";
	}

	// 註冊1-填寫帳號,密碼,統編頁面(純轉移POST)
	@RequestMapping(value = "/register/progress1", method = RequestMethod.POST)
	public String register_acc_pwd() {
		return "01MemberAction/RegpageAccPwd";
	}

	// 註冊1-檢查資料有無填入&帳號是否重複
	@RequestMapping(value = "/register/progress1_fail", method = RequestMethod.POST)
	public String register_acc_pwd_check(@RequestParam(name = "useracc") String useracc,
			@RequestParam("userpwd") String userpwd, @RequestParam(name = "userpwd2") String userpwd2,
			@RequestParam(name = "conumber") String conumber, @RequestParam(name = "memberType") String memberType) {
		HttpSession session = request.getSession();

		boolean error = false;
		if (conumber.length() != 8 || !conumber.matches("[\\d]+")) {
			request.setAttribute("msgError", "統一編號格式錯誤，請重新輸入");
			error = true;
		}

		if (!userpwd.equals(userpwd2)) {
			request.setAttribute("msgError", "密碼判斷錯誤，請重新輸入");
			error = true;
		}

		if (useracc == null || useracc.length() == 0 || userpwd == null || userpwd.length() == 0 || userpwd2 == null
				|| userpwd2.length() == 0 || conumber == null || conumber.length() == 0) {
			request.setAttribute("msgError", "所有內容不可留白，請重新輸入");
			error = true;
		}

		if (error) {
			return "01MemberAction/RegpageAccPwd";
		}
		System.out.println(memberType);
		boolean check_acc = false;
		if (memberType.equals("customer")) {
			if (cservice.selectOnebyacc(useracc) == null) {
				check_acc = true;
			}
		} else if (memberType.equals("shop")) {
			if (suservice.selectOnebyacc(useracc) == null) {
				check_acc = true;
			}
		}
		System.out.println(check_acc);
		if (check_acc != true) {
			request.setAttribute("msgError", "該帳號已存在，請重新輸入");
			return "01MemberAction/RegpageAccPwd";
		}

		Map<String, String> register_data = new HashMap<String, String>();
		register_data.put("acc", useracc);
		register_data.put("pwd", userpwd);
		register_data.put("conumber", conumber);

		session.setAttribute("register_data", register_data);
		session.setAttribute("memberType", memberType);

		if (memberType.equals("customer")) {
			session.setAttribute("display_s", "none");
		} else if (memberType.equals("shop")) {
			session.setAttribute("display_c", "none");
		}

		return "redirect: " + request.getContextPath() + "/welcome/register/progress2";
	}

	// 註冊2-填寫會員資料頁面(純轉移)
	@RequestMapping(value = "/register/progress2", method = RequestMethod.GET)
	public String register_data() {
		return "01MemberAction/RegpageInfo";
	}

	// 註冊2-檢查資料有無填入
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/register/progress2_fail", method = RequestMethod.POST)
	public String register_data_check(@RequestParam(name = "company_name") String company_name,
			@RequestParam(name = "company_ph") String company_ph,
			@RequestParam(name = "company_address") String company_address,
			@RequestParam(name = "contact_name") String contact_name,
			@RequestParam(name = "contact_ph") String contact_ph, @RequestParam(name = "email") String email,
			@RequestParam(name = "invoice") String invoice) {
		HttpSession session = request.getSession();

		Map<String, String> register_data = (Map<String, String>) session.getAttribute("register_data");
		register_data.put("company_name", company_name);
		register_data.put("company_ph", company_ph);
		register_data.put("company_address", company_address);
		register_data.put("contact_name", contact_name);
		register_data.put("contact_ph", contact_ph);
		register_data.put("email", email);
		register_data.put("invoice", invoice);

		session.setAttribute("register_data", register_data);

		boolean errors = false;
		if (company_ph.length() != 10 || !company_ph.matches("[\\d]+") || contact_ph.length() != 10
				|| !contact_ph.matches("[\\d]+")) {
			request.setAttribute("msgError", "電話格式錯誤");
			errors = true;
		}

		if (company_name == null || company_name.length() == 0 || company_ph == null || company_ph.length() == 0
				|| company_address == null || company_address.length() == 0 || contact_name == null
				|| contact_name.length() == 0 || contact_ph == null || contact_ph.length() == 0 || email == null
				|| email.length() == 0 || invoice == null || invoice.length() == 0) {
			request.setAttribute("msgError", "基本資料不可留白");
			errors = true;
		}

		if (errors) {
			return "01MemberAction/RegpageInfo";
		}

		return "redirect: " + request.getContextPath() + "/welcome/register/progress3";
	}

	// 註冊3-確認會員資料頁面(純轉移)
	@RequestMapping(value = "/register/progress3", method = RequestMethod.GET)
	public String register_last_confirm() {
		return "01MemberAction/RegpageCheck";
	}

	// 註冊新帳戶
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/register/progress3_fail", method = RequestMethod.POST)
	public String register_confirm(@RequestParam(name = "terms", required = false) String check) {
		HttpSession session = request.getSession();

		if (check == null || !check.equals("true")) {
			request.setAttribute("msgError", "請勾選同意服務條款");
			return "01MemberAction/RegpageCheck";
		}

		Map<String, String> data = (Map<String, String>) session.getAttribute("register_data");
		String memberType = (String) session.getAttribute("memberType");

		if (memberType.equals("customer")) {
			Customer cus = new Customer(data.get("acc"), data.get("pwd"), data.get("company_name"),
					data.get("company_ph"), data.get("company_address"), data.get("contact_name"),
					data.get("contact_ph"), data.get("email"), data.get("conumber"), data.get("invoice"));
			cus.setCstmr_vip("unverified");
			cus.setCstmr_image("default.jpg");
			cus.setShopping_list("[]");
			cservice.insert(cus);
		} else if (memberType.equals("shop")) {
			Supply sup = new Supply(data.get("acc"), data.get("pwd"), data.get("company_name"), data.get("company_ph"),
					data.get("company_address"), data.get("contact_name"), data.get("contact_ph"), data.get("email"),
					data.get("conumber"), data.get("invoice"));
			sup.setSupply_vip("unverified");
			sup.setSupply_image("default.jpg");
			suservice.insert(sup);
		}

		session.invalidate();

		return "redirect: " + request.getContextPath() + "/welcome/register/login/" + memberType;
	}

	// 寄信件(註冊驗證) + 回登入頁面
	@RequestMapping(value = "/register/login/{memberType}", method = RequestMethod.GET)
	public String register_mail(@PathVariable String memberType)
			throws AddressException, MessagingException, IOException {
		String mailUser = env.getRequiredProperty("mailUser");
		String mailPassword = env.getRequiredProperty("mailPassword");

		if (memberType.equals("customer")) {
			GmailService_C gmailservice = new GmailService_C(mailUser, mailPassword);
			int cstmr_id = cservice.select_newest_id();
			Customer cus = cservice.selectOne(cstmr_id);

			gmailservice.validationLink(cus);
		} else if (memberType.equals("shop")) {
			GmailService_S gmailservicesupply = new GmailService_S(mailUser, mailPassword);
			int supply_id = suservice.select_newest_id();
			Supply sup = suservice.selectOne(supply_id);

			gmailservicesupply.validationLink(sup);
		}

		return "redirect: " + request.getContextPath() + "/welcome/regist_success";
	}

	// 註冊成功頁面(純轉移)
	@RequestMapping(value = "/regist_success", method = RequestMethod.GET)
	public String regist_success() {
		request.setAttribute("error", "註冊成功，請查收驗證郵件");
		return "00MainPage/WarningError";
	}

}
