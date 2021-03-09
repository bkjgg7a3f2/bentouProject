package _02_controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import _01_model._01_customer.CustomerService;
import _01_model._02_supply.SupplyService;

@Controller
public class NoFilterPage {
	@Autowired
	private HttpServletRequest request;

	private CustomerService cservice;
	private SupplyService suservice;

	@Autowired
	private NoFilterPage(CustomerService cservice, SupplyService suservice) {
		this.cservice = cservice;
		this.suservice = suservice;
	}

	@RequestMapping(path = "/TopSec")
	public String processIncTopPage() {
		String dirPage = "frame/TopSec";
		HttpSession session = request.getSession();

		boolean loginsta = Boolean.parseBoolean((String) session.getAttribute("bentou_loginstatus"));
		String memberType = (String) session.getAttribute("memberType");
		if (loginsta == true && memberType != null) {
			if (memberType.equals("customer")) {
				int cstmr_id = (int) session.getAttribute("cstmr_id");
				String name = cservice.selectOne(cstmr_id).getCstmr_name();
				request.setAttribute("cstmracc_name", name);
				dirPage = "frame/custTopSec";
			} else if (memberType.equals("shop")) {
				int supply_id = (int) session.getAttribute("supply_id");
				String name = suservice.selectOne(supply_id).getSupply_name();
				request.setAttribute("supplyacc_name", name);
				dirPage = "frame/shopTopSec";
			}
		}
		return dirPage;
	}

	@RequestMapping(path = "/FooterSec")
	public String processIncFooterPage() {
		HttpSession session = request.getSession();
		boolean loginsta = Boolean.parseBoolean((String) session.getAttribute("bentou_loginstatus"));
		if (loginsta == true) {
			request.setAttribute("display", "none");
		}
		return "frame/AboutUs";
	}

	@RequestMapping(path = "/BoostrapSetting")
	public String processIncBoostrap() {
		return "frame/BoostrapSet";
	}
}
