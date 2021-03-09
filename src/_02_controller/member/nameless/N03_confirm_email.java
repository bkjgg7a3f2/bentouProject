package _02_controller.member.nameless;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import _01_model._01_customer.Customer;
import _01_model._01_customer.CustomerService;
import _01_model._02_supply.Supply;
import _01_model._02_supply.SupplyService;

@Controller
@RequestMapping(value = "/welcome")
public class N03_confirm_email {
	@Autowired
	private HttpServletRequest request;

	private CustomerService cservice;
	private SupplyService suservice;

	@Autowired
	public N03_confirm_email(CustomerService cservice, SupplyService suservice) {
		this.cservice = cservice;
		this.suservice = suservice;
	}

	// 註冊驗證(透過信件) + 註冊成功頁面 + 自動登入
	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public String verify() {
		HttpSession session = request.getSession();

		String acc = request.getParameter("acc");
		String email = request.getParameter("email");
		String memberType = request.getParameter("mem");

		if (memberType.equals("cus")) {
			Customer cus = cservice.selectOnebyacc(acc);
			if (cus != null) {
				if (cus.getCstmr_email().equals(email) && cus.getCstmr_vip().equals("unverified")) {
					cus.setCstmr_vip("member");
					cservice.update_vip(cus);

					session.setAttribute("bentou_loginstatus", "true");
					session.setAttribute("memberType", "customer");
					session.setAttribute("cstmr_id", cus.getCstmr_id());

					request.setAttribute("error", "該帳號啟用成功");
				} else if (cus.getCstmr_vip().equals("banned")) {
					request.setAttribute("warning", "該帳號已停權，請聯絡管理者");
				} else {
					request.setAttribute("warning", "已舉報不正當行為");
				}
			} else {
				request.setAttribute("warning", "已舉報不正當行為");
			}
		} else if (memberType.equals("sup")) {
			Supply sup = suservice.selectOnebyacc(acc);
			if (sup != null) {
				if (sup.getSupply_email().equals(email) && sup.getSupply_vip().equals("unverified")) {
					sup.setSupply_vip("member");
					suservice.update_vip(sup);

					session.setAttribute("bentou_loginstatus", "true");
					session.setAttribute("memberType", "supply");
					session.setAttribute("supply_id", sup.getSupply_id());

					request.setAttribute("error", "該帳號啟用成功");
				} else if (sup.getSupply_vip().equals("banned")) {
					request.setAttribute("warning", "該帳號已停權，請聯絡管理者");
				} else {
					request.setAttribute("warning", "已舉報不正當行為");
				}
			} else {
				request.setAttribute("warning", "已舉報不正當行為");
			}
		} else {
			request.setAttribute("warning", "已舉報不正當行為");
		}

		return "00MainPage/WarningError";
	}

}
