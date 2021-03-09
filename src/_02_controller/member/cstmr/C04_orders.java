package _02_controller.member.cstmr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import _01_model._02_supply.SupplyService;
import _01_model._04_orders.Orders;
import _01_model._04_orders.OrdersService;
import _01_model._05_orders_detail.Details;
import _01_model._05_orders_detail.DetailsService;
import _01_model._06_comment.Comment;
import _01_model._06_comment.CommentService;

@Controller
@RequestMapping(value = "/cstmr")
public class C04_orders {
	@Autowired
	private HttpServletRequest request;

	private SupplyService suservice;
	private OrdersService oservice;
	private DetailsService dservice;
	private CommentService coservice;

	@Autowired
	private C04_orders(SupplyService suservice, OrdersService oservice, DetailsService dservice,
			CommentService coservice) {
		this.suservice = suservice;
		this.oservice = oservice;
		this.dservice = dservice;
		this.coservice = coservice;
	}

	// 顯示訂單紀錄(JSON型式)
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	private String show_orders() {
		HttpSession session = request.getSession();
		int cstmr_id = (int) session.getAttribute("cstmr_id");

		ArrayList<Orders> o = oservice.selectAll_cstmr_view(cstmr_id);

		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < o.size(); i++) {
			Orders od = o.get(i);
			int order_supply_id = od.getOrder_supply_id();
			String supply_name = suservice.selectOne(order_supply_id).getSupply_name();//店家名
			JSONObject jsonObject = C04_orders.jsonObject_order_data(od, supply_name);//拿到給顧客看的訂單資料

			String order_id = od.getOrder_id();
			ArrayList<Details> d = dservice.selectAllbyOrder_id(new Details(order_id));
			JSONObject jsonObject_alter = C04_orders.price_sum_and_confirm(jsonObject, d);//這裡會計算商品總額和確認狀態

			jsonArray.put(jsonObject_alter);
		}
		request.setAttribute("JSON_cstmr_orders", jsonArray);

		return "02CustomerPages/03OrderPages/custOrderOverAll";
	}

	// 顯示選定訂單之明細(同一商家)(Map型式+JSON型式)
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/orders/{order_id}", method = RequestMethod.GET)
	private String show_details(@PathVariable String order_id) {
		HttpSession session = request.getSession();

		Orders od = oservice.selectOne(order_id);

		if (od != null) {
			if (od.getOrder_cstmr_id() != (int) session.getAttribute("cstmr_id")) {
				request.setAttribute("warning", "請勿搜尋不存在，或是非自己相關之訂單內容！");
				return "00MainPage/WarningError";
			}
			String supply_name = suservice.selectOne(od.getOrder_supply_id()).getSupply_name();

			Map map_order = C04_orders.view_cstmr_order_data(od, supply_name);

			request.setAttribute("Map_cstmr_order_data", map_order);

			ArrayList<Details> d = dservice.selectAllbyOrder_id(new Details(order_id));
			JSONArray details = C04_orders.view_details(d);

			request.setAttribute("JSON_cstmr_details", details);

			// 判斷是否有評論
			Comment com = coservice.selectOne(order_id);
			if (com != null) {
				request.setAttribute("comment", "false");
			} else {
				request.setAttribute("comment", "true");
			}

			return "02CustomerPages/03OrderPages/custOrderDetail";
		} else {
			request.setAttribute("warning", "請勿搜尋不存在，或是非自己相關之訂單內容！");
			return "00MainPage/WarningError";
		}
	}

	// 取消選定訂單之明細(同一商家)
	@RequestMapping(value = "/orders/cancel", method = RequestMethod.POST)
	private String details_cancel(@RequestParam(name = "order_id") String order_id) {
		Map<Integer, Integer> map_id = dservice.map_details(order_id);

		for (int i = 0; i < map_id.size(); i++) {
			Details de = new Details(map_id.get(i + 1), "Cancelled");
			dservice.update(de);
		}

		return "redirect: " + request.getContextPath() + "/cstmr/orders/" + order_id;
	}

	// 以下為上述Controller內之使用方法
	// 設定訂單Map屬性
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map view_cstmr_order_data(Orders od, String supply_name) {
		Map map = new HashMap();
		map.put("order_id", od.getOrder_id());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		map.put("order_date", sdf.format(od.getOrder_date()));
		map.put("order_address", od.getOrder_address());
		map.put("expected_arrivetime", sdf.format(od.getExpected_arrivetime()));
		map.put("conumber", od.getCstmr_conumber());
		map.put("invoice", od.getCstmr_invoice());
		map.put("supply_name", supply_name);

		return map;
	}

	// 顯示: 各訂單明細之 商品名稱,價格,數量,確認狀態
	// 不顯示: key-設定按鈕,controller用
	private static JSONArray view_details(ArrayList<Details> d) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < d.size(); i++) {
			Details de = d.get(i);

			JSONObject jsonObject_details = new JSONObject();
			jsonObject_details.put("key", i + 1);
			jsonObject_details.put("commodity_name", de.getCommodity_name());
			jsonObject_details.put("commodity_price", de.getCommodity_price());
			jsonObject_details.put("order_quantity", de.getOrder_quantity());

			String order_confirm = de.getOrder_confirm();
			String confirm;

			if (order_confirm.equals("Agree")) {
				confirm = "已受理";
			} else if (order_confirm.equals("Denied")) {
				confirm = "不受理";
			} else if (order_confirm.equals("Cancelled")) {
				confirm = "已取消";
			} else {
				confirm = "未受理";
			}

			jsonObject_details.put("order_confirm", confirm);
			jsonArray.put(jsonObject_details);
		}

		return jsonArray;
	}

	// 設定各訂單之 id,廠商,訂購日期,地址,預計送達日期
	// order_id-另設按鈕,controller用
	private static JSONObject jsonObject_order_data(Orders od, String supply_name) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("order_id", od.getOrder_id());
		jsonObject.put("order_supply_name", supply_name);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		jsonObject.put("order_date", sdf.format(od.getOrder_date()));
		jsonObject.put("order_address", od.getOrder_address());
		jsonObject.put("expected_arrivetime", sdf.format(od.getExpected_arrivetime()));
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
				confirm = "部分無法受理";
			} else {
				if (checklist.indexOf(1) == -1) {
					confirm = "未受理";
				} else if (checklist.indexOf(0) == -1) {
					confirm = "已受理";
				} else {
					confirm = "部分受理";
				}
			}
		} else {
			confirm = "已取消";
		}
		jsonObject.put("confirm", confirm);

		return jsonObject;
	}
}
