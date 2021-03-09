package _02_controller.backStage;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import _01_model._01_customer.Customer;
import _01_model._01_customer.CustomerService;
import _01_model._02_supply.Supply;
import _01_model._02_supply.SupplyService;

@Controller
@RequestMapping(value = "/manager")
public class B01_main {
	@Autowired
	private HttpServletRequest request;

	private CustomerService cservice;
	private SupplyService suservice;

	@Autowired
	public B01_main(CustomerService cservice, SupplyService suservice) {
		this.cservice = cservice;
		this.suservice = suservice;
	}

	// 轉移登入頁面
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login_check() {
		HttpSession session = request.getSession();
		session.invalidate();
		return "Backstage/00Main/Login";
	}

	// 登入檢驗
	@RequestMapping(value = "/login_fail", method = RequestMethod.POST)
	public String login_check(@RequestParam(name = "useracc") String useracc,
			@RequestParam(name = "userpwd") String userpwd) {
		HttpSession session = request.getSession();

		if (useracc == null || useracc.length() == 0 || userpwd == null || userpwd.length() == 0) {
			request.setAttribute("msgError", "請輸入帳號及密碼");
		}

		if (useracc.equals("123") && userpwd.equals("123")) {
			session.setAttribute("loginSuccess", "郭揚揚");
			session.setAttribute("backstage_loginstatus", true);
			return "redirect: " + request.getContextPath() + "/manager/HomePage";
		} else {
			request.setAttribute("msgError", "帳號或密碼錯誤，請重新輸入");
		}

		return "Backstage/00Main/Login";
	}

	// 首頁 (顯示會員通知、更新通知)
	@RequestMapping(value = "/HomePage", method = RequestMethod.GET)
	public String HomePage() {
		HttpSession session = request.getSession();

		ArrayList<Customer> cc = cservice.selectAll();
		int cus_count = 0;
		for (int i = 0; i < cc.size(); i++) {
			Customer cus = cc.get(i);
			String vip = cus.getCstmr_vip();
			if (!vip.equals("member") && !vip.equals("vip") && !vip.equals("unverified") && !vip.equals("banned")) {
				cus_count++;
			}
		}

		ArrayList<Supply> ss = suservice.selectall();
		int sup_count = 0;
		for (int i = 0; i < ss.size(); i++) {
			Supply sup = ss.get(i);
			String vip = sup.getSupply_vip();
			if (!vip.equals("member") && !vip.equals("vip") && !vip.equals("unverified") && !vip.equals("banned")) {
				sup_count++;
			}
		}

		if (cus_count > 0 && sup_count > 0) {
			request.setAttribute("msg", "有"+cus_count+"家消費者帳戶、"+sup_count+"家廠商帳戶 等待審核");
		} else if (cus_count > 0 && sup_count == 0) {
			request.setAttribute("msg", "有"+cus_count+"家消費者帳戶 等待審核");
		} else if (cus_count == 0 && sup_count > 0) {
			request.setAttribute("msg", "有"+sup_count+"家廠商帳戶 等待審核");
		} else {
			request.setAttribute("msg", "無最新資訊");
		}

		if (session.getAttribute("message") == null) {
			session.setAttribute("message", "<hr>");
		}

		return "Backstage/00Main/HomePage";
	}

	// 登出
	@RequestMapping(value = "/login_out", method = RequestMethod.GET)
	public String view_login() {
		return "redirect: " + request.getContextPath() + "/manager/";
	}

}
