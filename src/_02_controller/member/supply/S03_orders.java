package _02_controller.member.supply;

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

import _01_model._01_customer.CustomerService;
import _01_model._04_orders.Orders;
import _01_model._04_orders.OrdersService;
import _01_model._05_orders_detail.Details;
import _01_model._05_orders_detail.DetailsService;

@Controller
@RequestMapping(value = "/shop")
public class S03_orders {
	@Autowired
	private HttpServletRequest request;

	private CustomerService cservice;
	private OrdersService oservice;
	private DetailsService dservice;

	@Autowired
	private S03_orders(CustomerService cservice, OrdersService oservice, DetailsService dservice) {
		this.cservice = cservice;
		this.oservice = oservice;
		this.dservice = dservice;
	}

	// 顯示訂單紀錄(JSON型式)
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	private String show_orders() {
		HttpSession session = request.getSession();
		int supply_id = (int) session.getAttribute("supply_id");

		ArrayList<Orders> o = oservice.selectAll_supply_view(supply_id);

		JSONArray jsonArray = new JSONArray();

		for (int x = 0; x < o.size(); x++) {
			Orders od = o.get(x);
			int order_cstmr_id = od.getOrder_cstmr_id();
			String cstmr_name = cservice.selectOne(order_cstmr_id).getCstmr_name();
			JSONObject jsonObject = S03_orders.jsonObject_order_data(od, cstmr_name);

			String order_id = od.getOrder_id();
			ArrayList<Details> d = dservice.selectAllbyOrder_id(new Details(order_id));
			JSONObject jsonObject_alter = S03_orders.price_sum_and_confirm(jsonObject, d);

			jsonArray.put(jsonObject_alter);
		}

		request.setAttribute("JSON_supply_orders", jsonArray);

		return "03ShopPages/03ShopOrderPages/shopOrderOverAll";
	}

	// 顯示選定訂單之明細(Map+JSON型式)
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/orders/{order_id}", method = RequestMethod.GET)
	private String show_details(@PathVariable String order_id) {
		HttpSession session = request.getSession();

		Orders od = oservice.selectOne(order_id);

		if (od != null) {
			if (od.getOrder_supply_id() != (int) session.getAttribute("supply_id")) {
				request.setAttribute("warning", "請勿搜尋不存在，或是非自己相關之訂單內容！");
				return "00MainPage/WarningError";
			}
			String cstmr_name = cservice.selectOne(od.getOrder_cstmr_id()).getCstmr_name();

			Map map_order = S03_orders.view_cstmr_order_data(od, cstmr_name);//拿顧客資料而已

			request.setAttribute("Map_cstmr_order_data", map_order);

			ArrayList<Details> d = dservice.selectAllbyOrder_id(new Details(order_id));//根據訂單ID拿Detail
			JSONArray details = S03_orders.view_details(d);//這裡會判斷訂單狀態

			request.setAttribute("JSON_cstmr_details", details);

			return "03ShopPages/03ShopOrderPages/shopOrderDetail";
		} else {
			request.setAttribute("warning", "請勿搜尋不存在，或是非自己相關之訂單內容！");
			return "00MainPage/WarningError";
		}

	}

	// 確認或拒絕選定之訂單明細
	@RequestMapping(value = "/orders/check/{order_id}/{key}", method = RequestMethod.POST)
	private String details_check(@PathVariable("order_id") String order_id, @PathVariable("key") Integer key,
			@RequestParam(name = "submit") String submit) {
		Map<Integer, Integer> map_id = dservice.map_details(order_id);

		if (submit.equals("接受")) {
			Details de = new Details(map_id.get(key), "Agree");
			dservice.update(de);
		} else if (submit.equals("拒絕")) {
			Details de = new Details(map_id.get(key), "Denied");
			dservice.update(de);
		}

		return "redirect: " + request.getContextPath() + "/shop/orders/" + order_id;
	}

	// 以下為上述Controller內之使用方法
	// 設定各訂單之 id,消費者,訂購日期,地址,預計送達日期,電話
	// order_id-另設按鈕,controller用
	private static JSONObject jsonObject_order_data(Orders od, String cstmr_name) {
		String order_id = od.getOrder_id();
		String order_address = od.getOrder_address();
		Date expected_arrivetime = od.getExpected_arrivetime();
		String order_ph = od.getOrder_ph();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("order_id", order_id);
		jsonObject.put("order_cstmr_name", cstmr_name);
		jsonObject.put("order_address", order_address);
		jsonObject.put("expected_arrivetime", sdf.format(expected_arrivetime));
		jsonObject.put("order_ph", order_ph);

		return jsonObject;
	}

	// 設定各訂單之 預計總額,確認狀態
	private static JSONObject price_sum_and_confirm(JSONObject jsonObject, ArrayList<Details> d) {
		int price_sum = 0;
		for (int j = 0; j < d.size(); j++) {
			Details de = d.get(j);

			String order_confirm = de.getOrder_confirm();
			int price;

			if (order_confirm.equals("Denied") || order_confirm.equals("Cancelled")) {
				price = 0;
			} else {
				price = de.getCommodity_price() * de.getOrder_quantity();
			}
			price_sum = price_sum + price;
		}
		jsonObject.put("price_sum", price_sum);

		ArrayList<Integer> checklist = new ArrayList<>();
		for (int j = 0; j < d.size(); j++) {
			Details de = d.get(j);
			String order_confirm = de.getOrder_confirm();
			if (order_confirm.equals("Agree")) {
				checklist.add(1);
			} else if (order_confirm.equals("Denied")) {
				checklist.add(-1);
			} else if (order_confirm.equals("Unconfirm")) {
				checklist.add(0);
			}
		}
		String confirm;
		if (!checklist.isEmpty()) {
			if (checklist.contains(-1)) {
				confirm = "部分不受理";
			} else {
				if (checklist.indexOf(1) == -1) {
					confirm = "未確認";
				} else if (checklist.indexOf(0) == -1) {
					confirm = "已受理";
				} else {
					confirm = "部分受理";
				}
			}
		} else {
			confirm = "顧客取消";
		}
		jsonObject.put("confirm", confirm);

		return jsonObject;
	}

	// 設定訂單Map屬性
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map view_cstmr_order_data(Orders od, String cstmr_name) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Map map = new HashMap();
		map.put("order_id", od.getOrder_id());
		map.put("order_date", sdf.format(od.getOrder_date()));
		map.put("cstmr_name", cstmr_name);
		map.put("order_cstmr_name", od.getCstmr_name());
		map.put("order_ph", od.getOrder_ph());
		map.put("order_address", od.getOrder_address());
		map.put("expected_arrivetime", sdf.format(od.getExpected_arrivetime()));
		map.put("cstmr_conumber", od.getCstmr_conumber());
		map.put("cstmr_invoice", od.getCstmr_invoice());

		return map;
	}

	// 顯示: 各訂單明細之 商品名稱,價格,數量,確認狀態
	// 不顯示: key-設定按鈕,controller用
	private static JSONArray view_details(ArrayList<Details> d) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < d.size(); i++) {
			Details de = d.get(i);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("key", i + 1);
			jsonObject.put("commodity_name", de.getCommodity_name());
			jsonObject.put("commodity_price", de.getCommodity_price());
			jsonObject.put("order_quantity", de.getOrder_quantity());

			String order_confirm = de.getOrder_confirm();
			String confirm;

			if (order_confirm.equals("Agree")) {
				confirm = "已受理";
			} else if (order_confirm.equals("Denied")) {
				confirm = "不受理";
			} else if (order_confirm.equals("Cancelled")) {
				confirm = "已取消";
			} else {
				confirm = "未確認";
			}

			jsonObject.put("order_confirm", confirm);
			jsonArray.put(jsonObject);
		}
		return jsonArray;
	}
}
