package _02_controller.member.cstmr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
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
import _01_model._03_sales.SalesService;
import _01_model._04_orders.Orders;
import _01_model._04_orders.OrdersService;
import _01_model._05_orders_detail.Details;
import _01_model._05_orders_detail.DetailsService;

@Controller
@RequestMapping(value = "/cstmr")
public class C03_write_into_orders {
	@Autowired
	private HttpServletRequest request;

	private CustomerService cservice;
	private SupplyService suservice;
	private SalesService saservice;
	private OrdersService oservice;
	private DetailsService dservice;

	@Autowired
	private C03_write_into_orders(CustomerService cservice, SupplyService suservice, SalesService saservice,
			OrdersService oservice, DetailsService dservice) {
		this.cservice = cservice;
		this.suservice = suservice;
		this.saservice = saservice;
		this.oservice = oservice;
		this.dservice = dservice;
	}

	// 確認購物單(JSON形式) + 填收件資料(Map型式) POST
	@RequestMapping(value = "/shopping_undone", method = RequestMethod.GET)
	private String shoppingcart_confirm1() throws JSONException, ParseException {
		HttpSession session = request.getSession();
		int cstmr_id = (int) session.getAttribute("cstmr_id");

		String list = cservice.selectOne(cstmr_id).getShopping_list();

		if (list.equals("[]")) {
			request.setAttribute("msg", "尚未購物");
			return "redirect: " + request.getContextPath() + "/cstmr/shoppingcart";
		}

		boolean sum_under_limit = false;
		JSONArray jsonarray = new JSONArray();

		ArrayList<Supply> s = suservice.selectall();
		for (int i = 0; i < s.size(); i++) {
			Supply sal = s.get(i);
			int supply_id = sal.getSupply_id();
			int sum = saservice.supply_limit_sum(supply_id, list);

			if (sum != 0 && sum < sal.getLimit()) {
				JSONObject jsonobject = new JSONObject();
				jsonobject.put("supply_name", sal.getSupply_name() + ": " + sal.getLimit() + "元");
				jsonarray.put(jsonobject);
				sum_under_limit = true;
			}
		}

		if (sum_under_limit) {
			JSONArray name_list = cservice.view_shoppinglist(new Customer(cstmr_id));
			request.setAttribute("JSON_shoppingcart", name_list);
			request.setAttribute("JSON_supply_name", jsonarray);
			request.setAttribute("msg", "未達最低消費金額");
			return "02CustomerPages/02Shopping/custShoppingCart";
		}

		Map<String, String> map_cstmr_data = cservice.form_cstmr_data(new Customer(cstmr_id));
		request.setAttribute("Map_cstmr_data", map_cstmr_data);

		JSONArray name_list = cservice.view_shoppinglist(new Customer(cstmr_id));
		request.setAttribute("JSON_shoppingcart_confirm", name_list);
		return "02CustomerPages/02Shopping/custOrderInfo";
	}

	// 下訂單最後步驟
	@RequestMapping(value = "/shoppingcart/confirm_fail", method = RequestMethod.POST)
	private String shoppingcart_confirm2(@RequestParam(name = "cstmr_contact") String cstmr_contact,
			@RequestParam(name = "order_ph") String order_ph,
			@RequestParam(name = "order_address") String order_address,
			@RequestParam(name = "expected_arrivetime") String expected_arrivetime,
			@RequestParam(name = "cstmr_conumber") String cstmr_conumber,
			@RequestParam(name = "cstmr_invoice") String cstmr_invoice) throws JSONException, ParseException {
		HttpSession session = request.getSession();

		int cstmr_id = (int) session.getAttribute("cstmr_id");
		String errors = C03_write_into_orders.check_order_data(cstmr_contact, order_ph, order_address,
				expected_arrivetime, cstmr_conumber, cstmr_invoice);

		if (errors != null) {
			Map<String, String> map_cstmr_data = new HashMap<String, String>();
			map_cstmr_data.put("cstmr_contact", cstmr_contact);
			map_cstmr_data.put("order_ph", order_ph);
			map_cstmr_data.put("order_address", order_address);
			map_cstmr_data.put("expected_arrivetime", expected_arrivetime);
			map_cstmr_data.put("cstmr_conumber", cstmr_conumber);
			map_cstmr_data.put("cstmr_invoice", cstmr_invoice);
			request.setAttribute("Map_cstmr_data", map_cstmr_data);

			JSONArray name_list;

			name_list = cservice.view_shoppinglist(new Customer(cstmr_id));

			request.setAttribute("JSON_shoppingcart_confirm", name_list);
			request.setAttribute("OrderInfoMsg", errors);
			return "02CustomerPages/02Shopping/custOrderInfo";
		}

		// 設定Orders Bean(共同)

		int order_cstmr_id = cstmr_id;
		Date order_date = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date ex_time = sdf.parse(expected_arrivetime);

		Orders od = new Orders(order_cstmr_id, order_date, ex_time, cstmr_contact, order_ph, order_address,
				cstmr_conumber, cstmr_invoice);

		String id_list = cservice.selectOne(order_cstmr_id).getShopping_list();
		ArrayList<Integer> supply_id_list = saservice.supply_id_appear_once_list(id_list);

		session.setAttribute("Orders", od);
		session.setAttribute("id_list", id_list);
		session.setAttribute("order_date", order_date);
		session.setAttribute("supply_id_list", supply_id_list);

		return "redirect: " + request.getContextPath() + "/cstmr/shoppingcart/confirm/process/0";

	}

	// 下訂單最後步驟(填入Orders + Details資料表)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/shoppingcart/confirm/process/{key}", method = RequestMethod.GET)
	private String process_orders_details(@PathVariable int key) {
		HttpSession session = request.getSession();
		int order_cstmr_id = (int) session.getAttribute("cstmr_id");
		ArrayList<Integer> supply_id_list = (ArrayList<Integer>) session.getAttribute("supply_id_list");

		try {
			Orders od = (Orders) session.getAttribute("Orders");
			String id_list = (String) session.getAttribute("id_list");
			Date order_date = (Date) session.getAttribute("order_date");

			// 輸入Orders(廠商相關)
			int order_supply_id = supply_id_list.get(key);

			od.setOrder_supply_id(order_supply_id);

			od.setOrder_number(key + 1);

			SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmss");
			String order_date_str = ft.format(order_date);
			String order_number = String.valueOf(key + 1);
			String order_id = String.valueOf(order_cstmr_id) + String.valueOf(order_supply_id) + order_date_str
					+ order_number;
			od.setOrder_id(order_id);

			oservice.add(od);

			// 輸入(同一)廠商之Details
			ArrayList<Details> d = saservice.insertbySupply_id(order_supply_id, id_list);
			for (int j = 0; j < d.size(); j++) {
				Details de = d.get(j);

				de.setOrder_id(order_id);
				de.setOrder_supply_id(order_supply_id);
				de.setOrder_confirm("Unconfirm");
				dservice.add(de);
			}

			return "redirect: " + request.getContextPath() + "/cstmr/shoppingcart/confirm/process/" + (key + 1);

		} catch (IndexOutOfBoundsException e) {
			cservice.update_shoppinglist(new Customer(order_cstmr_id, "[]"));
			return "redirect: " + request.getContextPath() + "/cstmr/order_finish";
		}

	}

	// 轉移感謝頁面
	@RequestMapping(value = "/order_finish", method = RequestMethod.GET)
	private String order_finish() {
		HttpSession session = request.getSession();
		session.removeAttribute("Orders");
		session.removeAttribute("id_list");
		session.removeAttribute("order_date");
		session.removeAttribute("supply_id_list");
		return "02CustomerPages/02Shopping/custThankPage";
	}

	// 以下為上述Controller內之使用方法
	// 判斷訂單設定是否有空值
	private static String check_order_data(String cstmr_contact, String order_ph, String order_address,
			String expected_arrivetime, String cstmr_conumber, String cstmr_invoice) {
		ArrayList<Integer> errors = new ArrayList<Integer>();

		if (cstmr_contact == null || cstmr_contact.length() == 0) {
			errors.add(1);
		}
		if (order_ph == null || order_ph.length() == 0) {
			errors.add(1);
		}
		if (order_address == null || order_address.length() == 0) {
			errors.add(1);
		}
		if (expected_arrivetime == null || expected_arrivetime.length() == 0) {
			errors.add(1);
		}
		if (cstmr_conumber == null || cstmr_conumber.length() == 0) {
			errors.add(1);
		}
		if (cstmr_invoice == null || cstmr_invoice.length() == 0) {
			errors.add(1);
		}

		if (errors.contains(1)) {
			return "內容不可留白";
		}
		
		if (cstmr_conumber.length() != 8 || !cstmr_conumber.matches("[\\d]+")) {
			return "統一編號格式錯誤";
		}

		if (order_ph.length() != 10 || !order_ph.matches("[\\d]+")) {
			return "電話格式錯誤";
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			Date date=sdf.parse(expected_arrivetime);
			Date now=new Date();
			if(date.before(now)){
				return "時間逾時，請重新選擇";
			}
		} catch (ParseException e) {
			return "時間格式錯誤";
		}

		return null;
	}

}
