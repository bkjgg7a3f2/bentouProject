package _02_controller.backStage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import _01_model._01_customer.Customer;
import _01_model._01_customer.CustomerService;
import _01_model._02_supply.Supply;
import _01_model._02_supply.SupplyService;

@Controller
@RequestMapping(value = "/manager")
public class B02_memberlist {

	@Autowired
	private HttpServletRequest request;

	private CustomerService cusService;
	private SupplyService supService;

	@Autowired
	public B02_memberlist(CustomerService cusService, SupplyService supService) {
		this.cusService = cusService;
		this.supService = supService;
	}

	// 顯示所有消費者資料
	@RequestMapping(value = "/show_Customer", method = RequestMethod.GET)
	private String show_Customer() {
		ArrayList<Customer> cc = cusService.selectAll();
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < cc.size(); i++) {
			Customer cu = cc.get(i);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("cstmr_id", cu.getCstmr_id());
			jsonObject.put("cstmr_acc", cu.getCstmr_acc());
			jsonObject.put("cstmr_pwd", cu.getCstmr_pwd());
			jsonObject.put("cstmr_name", cu.getCstmr_name());
			jsonObject.put("cstmr_ph", cu.getCstmr_ph());
			jsonObject.put("cstmr_address", cu.getCstmr_address());
			jsonObject.put("cstmr_contact", cu.getCstmr_contact());
			jsonObject.put("cstmr_contactphnum", cu.getCstmr_contactphnum());
			jsonObject.put("cstmr_email", cu.getCstmr_email());
			jsonObject.put("cstmr_conumber", cu.getCstmr_conumber());
			jsonObject.put("cstmr_bankaccount", cu.getCstmr_bankaccount());
			jsonObject.put("cstmr_invoice", cu.getCstmr_invoice());

			String vip = "待審核...";
			if (cu.getCstmr_vip().equals("member")) {
				vip = "一般會員";
			} else if (cu.getCstmr_vip().equals("vip")) {
				vip = "付費會員";
			} else if (cu.getCstmr_vip().equals("banned")) {
				vip = "停權";
			} else if (cu.getCstmr_vip().equals("unverified")) {
				vip = "未認證";
			}

			jsonObject.put("cstmr_vip", vip);
			jsonArray.put(jsonObject);
		}
		request.setAttribute("JSON_Customer_ListAll", jsonArray.toString());
		return "Backstage/01Member/CustomerList";
	}

	// 顯示選定消費者帳號狀態 ///////更新
	@RequestMapping(value = "/Customer_preUpdate/{key}", method = RequestMethod.GET)
	public String Customer_preUpdate(@PathVariable String key) {
		int id = Integer.parseInt(key);
		Customer cus = cusService.selectOne(id);
		Map<String, String> cusmap = new HashMap<String, String>();
		cusmap.put("cstmr_id", key);
		cusmap.put("cstmr_acc", cus.getCstmr_acc());
		cusmap.put("cstmr_name", cus.getCstmr_name());
		cusmap.put("cstmr_ph", cus.getCstmr_ph());
		cusmap.put("cstmr_address", cus.getCstmr_address());
		cusmap.put("cstmr_contact", cus.getCstmr_contact());
		cusmap.put("cstmr_contactphnum", cus.getCstmr_contactphnum());
		cusmap.put("cstmr_email", cus.getCstmr_email());
		cusmap.put("cstmr_conumber", cus.getCstmr_conumber());
		cusmap.put("cstmr_bankaccount", cus.getCstmr_bankaccount());
		cusmap.put("cstmr_invoice", cus.getCstmr_invoice());

		if (cus.getCstmr_vip().equals("vip")) {
			cusmap.put("cstmr_vip_Y", "selected");
			cusmap.put("display", "none");
		} else if (cus.getCstmr_vip().equals("banned")) {
			cusmap.put("cstmr_vip_OUT", "selected");
			cusmap.put("display", "none");
		} else if (cus.getCstmr_vip().equals("member")) {
			cusmap.put("display", "none");
			cusmap.put("cstmr_vip_Y", "");
			cusmap.put("cstmr_vip_OUT", "");
		} else {
			request.setAttribute("num", cus.getCstmr_vip());
			cusmap.put("cstmr_vip_Y", "");
			cusmap.put("cstmr_vip_OUT", "");
		}

		request.setAttribute("MAP_Customer_PreUpdate", cusmap);

		return "Backstage/01Member/UpdateCustomerVIP";
	}

	// 更新選定消費者帳號狀態
	@RequestMapping(value = "/Customer_update", method = RequestMethod.POST)
	public String Customer_update(@RequestParam(name = "customer_id", required = false) String customer_id,
			@RequestParam(name = "cstmr_vip", required = false) String cstmr_vip) {
		HttpSession session = request.getSession();
		String mes = (String) session.getAttribute("message");

		int id = Integer.parseInt(customer_id);

		Customer cus = new Customer(id);
		cus.setCstmr_vip(cstmr_vip);
		cusService.update_vip(cus);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String date = sdf.format(new Date());

		session.setAttribute("message",
				date + "─────消費者\"" + cusService.selectOne(id).getCstmr_name() + "\"會員狀態-修改成功<br>" + mes);
		return "redirect: " + request.getContextPath() + "/manager/HomePage";
	}

	// 顯示所有廠商資料
	@RequestMapping(value = "/show_Supply", method = RequestMethod.GET)
	private String show_Supply() {
		ArrayList<Supply> ss = supService.selectall();
		JSONArray jsonArray = new JSONArray();

		for (int n = 0; n < ss.size(); n++) {
			Supply su = ss.get(n);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("supply_id", su.getSupply_id());
			jsonObject.put("supply_acc", su.getSupply_acc());
			jsonObject.put("supply_pwd", su.getSupply_pwd());
			jsonObject.put("supply_name", su.getSupply_name());
			jsonObject.put("supply_ph", su.getSupply_ph());
			jsonObject.put("supply_address", su.getSupply_address());
			jsonObject.put("supply_contact", su.getSupply_contact());
			jsonObject.put("supply_contactphnum", su.getSupply_contactphnum());
			jsonObject.put("supply_email", su.getSupply_email());
			jsonObject.put("supply_conumber", su.getSupply_conumber());
			jsonObject.put("supply_bankaccount", su.getSupply_bankaccount());
			jsonObject.put("supply_invoice", su.getSupply_invoice());
			jsonObject.put("supply_limit", su.getLimit());

			String vip = "待審核...";
			if (su.getSupply_vip().equals("member")) {
				vip = "一般會員";
			} else if (su.getSupply_vip().equals("vip")) {
				vip = "付費會員";
			} else if (su.getSupply_vip().equals("banned")) {
				vip = "停權";
			} else if (su.getSupply_vip().equals("unverified")) {
				vip = "未認證";
			}

			jsonObject.put("supply_vip", vip);
			jsonArray.put(jsonObject);
		}
		request.setAttribute("JSON_Supply_ListAll", jsonArray.toString());
		return "Backstage/01Member/SupplyList";
	}

	// 顯示選定廠商帳號狀態 ///////更新
	@RequestMapping(value = "/Supply_preUpdate/{key}", method = RequestMethod.GET)
	public String Supply_preUpdate(@PathVariable String key) {
		int id = Integer.parseInt(key);
		Supply sup = supService.selectOne(id);
		Map<String, String> supmap = new HashMap<String, String>();
		int Limit = sup.getLimit();
		String supply_limit = Integer.toString(Limit);

		supmap.put("supply_id", key);
		supmap.put("supply_acc", sup.getSupply_acc());
		supmap.put("supply_name", sup.getSupply_name());
		supmap.put("supply_ph", sup.getSupply_ph());
		supmap.put("supply_address", sup.getSupply_address());
		supmap.put("supply_contact", sup.getSupply_contact());
		supmap.put("supply_contactphnum", sup.getSupply_contactphnum());
		supmap.put("supply_email", sup.getSupply_email());
		supmap.put("supply_conumber", sup.getSupply_conumber());
		supmap.put("supply_bankaccount", sup.getSupply_bankaccount());
		supmap.put("supply_invoice", sup.getSupply_invoice());
		supmap.put("supply_limit", supply_limit);

		if (sup.getSupply_vip().equals("vip")) {
			supmap.put("supply_vip_Y", "selected");
		} else if (sup.getSupply_vip().equals("banned")) {
			supmap.put("supply_vip_OUT", "selected");
		} else {
			supmap.put("supply_vip_Y", "");
			supmap.put("supply_vip_OUT", "");
		}
		request.setAttribute("MAP_Supply_PreUpdate", supmap);

		return "Backstage/01Member/UpdateSupplyVIP";
	}

	// 修改選定廠商帳號狀態
	@RequestMapping(value = "/Supply_update", method = RequestMethod.POST)
	public String Supply_update(@RequestParam(name = "supply_id", required = false) String supply_id,
			@RequestParam(name = "supply_vip", required = false) String supply_vip) {
		HttpSession session = request.getSession();
		String mes = (String) session.getAttribute("message");
		int id = Integer.parseInt(supply_id);

		Supply sup = new Supply(id);
		sup.setSupply_vip(supply_vip);
		supService.update_vip(sup);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String date = sdf.format(new Date());

		session.setAttribute("message",
				date + "─────廠商\"" + supService.selectOne(id).getSupply_name() + "\"會員狀態-修改成功<br>" + mes);
		return "redirect: " + request.getContextPath() + "/manager/HomePage";
	}
}
