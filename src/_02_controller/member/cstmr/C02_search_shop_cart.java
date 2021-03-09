package _02_controller.member.cstmr;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import _01_model._01_customer.Customer;
import _01_model._01_customer.CustomerService;
import _01_model._02_supply.Supply;
import _01_model._02_supply.SupplyService;
import _01_model._03_sales.Sales;
import _01_model._03_sales.SalesService;
import _01_model._06_comment.Comment;
import _01_model._06_comment.CommentService;

@Controller
@RequestMapping(value = "/cstmr")
public class C02_search_shop_cart {
	@Autowired
	private HttpServletRequest request;

	private CustomerService customerService;
	private SupplyService supplyService;
	private SalesService salesService;
	private CommentService commentService;

	@Autowired
	private C02_search_shop_cart(CustomerService cservice, SupplyService suservice, SalesService saservice,
			CommentService coservice) {
		this.customerService = cservice;
		this.supplyService = suservice;
		this.salesService = saservice;
		this.commentService = coservice;
	}

	// 搜尋關鍵字
	@RequestMapping(value = "/search_word", method = RequestMethod.GET)
	private String search_word(@RequestParam(name = "n") String txt) {
		if (txt == null || txt.length() == 0) {
			return "redirect: " + request.getContextPath() + "/cstmr/";
		}
		ArrayList<Integer> supply_id_list = supplyService.search_Supply(txt);
		ArrayList<Sales> sa = salesService.search_Sales(txt);

		ArrayList<Integer> sales_supply_id_list = new ArrayList<Integer>();
		for (int i = 0; i < sa.size(); i++) {
			sales_supply_id_list.add(sa.get(i).getSupply_id());
		}

		List<Integer> list = sales_supply_id_list.stream().distinct().collect(Collectors.toList());

		List<Integer> list2 = new ArrayList<>(list);
		list2.removeAll(supply_id_list);
		supply_id_list.addAll(list2);
		Collections.sort(supply_id_list);

		JSONArray jsonArray_supply = new JSONArray();
		JSONArray jsonArray_sales = new JSONArray();
		for (int i = 0; i < supply_id_list.size(); i++) {
			Supply sup = supplyService.selectOne(supply_id_list.get(i));
			JSONObject jsonObject_supply = new JSONObject();
			jsonObject_supply.put("key", sup.getSupply_id());
			jsonObject_supply.put("image", sup.getSupply_image());
			jsonObject_supply.put("supply_name", sup.getSupply_name());
			jsonObject_supply.put("supply_address", sup.getSupply_address());
			jsonObject_supply.put("supply_ph", sup.getSupply_ph());
			jsonObject_supply.put("supply_email", sup.getSupply_email());

			Comment com = new Comment(sup.getSupply_id());
			ArrayList<Comment> c = commentService.selectAllbySupply_id(com);

			Double fraction_sum = 0.0;
			for (int j = 0; j < c.size(); j++) {
				Comment bb1 = c.get(j);
				int fraction = bb1.getCstmr_fraction();
				fraction_sum = fraction_sum + fraction;
			}
			Double d = fraction_sum / c.size();

			double fractionaverage = Math.round(d * 10.0) / 10.0;

			jsonObject_supply.put("score", fractionaverage);

			jsonArray_supply.put(jsonObject_supply);

			JSONObject jsonObject_sales = new JSONObject();
			jsonObject_sales.put("key", sup.getSupply_id());
			jsonArray_sales.put(jsonObject_sales);
		}

		for (int j = 0; j < jsonArray_sales.length(); j++) {
			JSONObject jsonObject = jsonArray_sales.getJSONObject(j);
			int supply_id = jsonObject.getInt("key");

			boolean contain_sales = false;
			for (int k = 0; k < sa.size(); k++) {
				Sales sal = sa.get(k);
				int id = sal.getSupply_id();
				String name = sal.getCommodity_name();

				if (id == supply_id) {
					if (!jsonObject.has("commodity_name")) {
						jsonObject.put("commodity_name", name);
					} else {
						String str = jsonObject.getString("commodity_name");
						jsonObject.put("commodity_name", str + "、" + name);
					}
					contain_sales = true;
				}
			}

			if (!contain_sales) {
				jsonObject.put("commodity_name", 0);
			}
		}

		if (!jsonArray_supply.isEmpty() && !jsonArray_sales.isEmpty()) {
			request.setAttribute("JSON_supply", jsonArray_supply);
			request.setAttribute("JSON_sales", jsonArray_sales);
			return "02CustomerPages/02Shopping/custShopSearch";
		} else {
			request.setAttribute("error", "查無結果");
			return "00MainPage/WarningError";
		}
	}

	// 顯示商家列表(JSON型式)
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	private String show_supply() {
		ArrayList<Supply> s = supplyService.selectall();
		JSONArray jsonArray = C02_search_shop_cart.view_supply(s);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonobject = jsonArray.getJSONObject(i);
			int supply_id = jsonobject.getInt("key");

			Comment com = new Comment(supply_id);
			ArrayList<Comment> c = commentService.selectAllbySupply_id(com);

			Double fraction_sum = 0.0;
			for (int j = 0; j < c.size(); j++) {
				Comment bb1 = c.get(j);
				int fraction = bb1.getCstmr_fraction();
				fraction_sum = fraction_sum + fraction;
			}
			Double d = fraction_sum / c.size();

			double fractionaverage = Math.round(d * 10.0) / 10.0;

			jsonobject.put("score", fractionaverage);
		}

		request.setAttribute("JSON_supply", jsonArray);
		return "02CustomerPages/02Shopping/custShopSearch";
	}

	// 顯示選定商家之商品(JSON型式)
	@RequestMapping(value = "/search/{key}", method = RequestMethod.GET)
	private String show_sales(@PathVariable String key) {
		HttpSession session = request.getSession();
		try {
			int supply_id = Integer.parseInt(key);
			Supply sup = supplyService.selectOne(supply_id);
			if (sup == null) {
				request.setAttribute("warning", "查無結果");
				return "00MainPage/WarningError";
			}

			ArrayList<Sales> s = salesService.selectAllbySupplyId(new Sales(supply_id));

			if (s.size() == 0) {
				request.setAttribute("error", "該商家尚未上架任何商品");
				return "00MainPage/WarningError";
			}

			JSONArray jsonArray = C02_search_shop_cart.view_sales(s);//顯示: 各(上架中)商品之 名稱,原價,葷素

			request.setAttribute("JSON_supply_sales", jsonArray);

			int cstmr_id = (int) session.getAttribute("cstmr_id");
			String shoppinglist = customerService.selectOne(cstmr_id).getShopping_list();

			int sum = salesService.supply_limit_sum(supply_id, shoppinglist);//根據購物車裡的商品計算小計

			request.setAttribute("sum", sum);
			request.setAttribute("limit", sup.getLimit());//低消

			request.setAttribute("supply_image", sup.getSupply_image());
			request.setAttribute("key", supply_id);
			return "02CustomerPages/02Shopping/custShopItems";
		} catch (NumberFormatException e) {
			request.setAttribute("warning", "查無結果");
			return "00MainPage/WarningError";
		}
	}

	// 加入購物清單(JSON型式之String)
	@RequestMapping(value = "/shoppingcart/add/{key}", method = RequestMethod.POST)
	private String shoppingcart_add(@PathVariable int key, @RequestParam(name = "commodity_id") String strcommodity_id,
			@RequestParam(name = "order_quantity") String strorder_quantity) {
		HttpSession session = request.getSession();

		int order_quantity = Integer.parseInt(strorder_quantity);
		if (strcommodity_id.length() == 0) {
			request.setAttribute("error", "請選擇餐點");
			return "redirect: " + request.getContextPath() + "/cstmr/search/" + key;
		}

		if (order_quantity == 0) {
			request.setAttribute("error", "請輸入正整數");
			return "redirect: " + request.getContextPath() + "/cstmr/search/" + key;
		}

		int cstmr_id = (int) session.getAttribute("cstmr_id");

		String shoppinglist = customerService.selectOne(cstmr_id).getShopping_list();//根據客戶ID查詢購物車清單
		int commodity_id = Integer.parseInt(strcommodity_id);
		
		//[{"date":"2021-02-22 05:40:32","commodity_id":1,"quantity":2,"key":1}]購物車清單裡面的資料長這樣
		//[{"date":"2021-02-22 05:50:15","commodity_id":1,"quantity":3,"key":1},{"date":"2021-02-22 05:50:48","commodity_id":2,"quantity":1,"key":2}]
		//[{"date":"2021-02-22 05:50:15","commodity_id":1,"quantity":3,"key":1},{"date":"2021-02-22 05:50:48","commodity_id":2,"quantity":1,"key":2},{"date":"2021-02-22 06:04:17","commodity_id":4,"quantity":1,"key":3}]
		String shoppinglist_alter = C02_search_shop_cart.create_or_add_list(shoppinglist, commodity_id, order_quantity);

		customerService.update_shoppinglist(new Customer(cstmr_id, shoppinglist_alter));//會先根據客戶ID查出那筆客戶資料，再把購物車資料塞進去更新
		request.setAttribute("shoppingmsg", "成功加入購物車");
		return "redirect: " + request.getContextPath() + "/cstmr/search/" + key;
	}

	// 顯示購物清單(JSON型式)
	@RequestMapping(value = "/shoppingcart", method = RequestMethod.GET)
	private String show_shoppingcart() throws JSONException, ParseException {
		HttpSession session = request.getSession();
		int cstmr_id = (int) session.getAttribute("cstmr_id");

		JSONArray name_list = customerService.view_shoppinglist(new Customer(cstmr_id));
		request.setAttribute("JSON_shoppingcart", name_list);
		return "02CustomerPages/02Shopping/custShoppingCart";
	}

	// 更改選定商品之數量 或 刪除選定商品(JSON型式)
	@RequestMapping(value = "/shoppingcart/update_or_delete/{key}", method = RequestMethod.POST)
	private String update_shoppingcart(@PathVariable Integer key,
			@RequestParam(name = "order_quantity") int order_quantity, @RequestParam(name = "submit") String submit) {
		HttpSession session = request.getSession();

		try {
			int cstmr_id = (int) session.getAttribute("cstmr_id");

			String id_list = customerService.selectOne(cstmr_id).getShopping_list();

			String new_shoppinglist = null;

			if (submit.equals("修改")) {
				new_shoppinglist = C02_search_shop_cart.update_quantity(key, order_quantity, new Customer(id_list));
			} else if (submit.equals("刪除")) {
				new_shoppinglist = C02_search_shop_cart.delete_list(key, new Customer(id_list));
			}

			customerService.update_shoppinglist(new Customer(cstmr_id, new_shoppinglist));

			return "redirect: " + request.getContextPath() + "/cstmr/shoppingcart";
		} catch (MethodArgumentTypeMismatchException e) {
			request.setAttribute("error", "請輸入正整數");
			return "00MainPage/WarningError";
		}
	}

	// 刪除購物清單 POST
	@RequestMapping(value = "/shoppingcart/delete", method = RequestMethod.GET)
	private String shoppingcart_delete() {
		HttpSession session = request.getSession();
		int cstmr_id = (int) session.getAttribute("cstmr_id");
		customerService.update_shoppinglist(new Customer(cstmr_id, "[]"));
		return "redirect: " + request.getContextPath() + "/cstmr/shoppingcart";
	}

	// 以下為上述Controller內之使用方法
	// 顯示: 各廠商之 圖0,名稱,地址,電話,信箱
	// 不顯示: key-設定按鈕,controller用
	private static JSONArray view_supply(ArrayList<Supply> s) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < s.size(); i++) {
			Supply su = s.get(i);

			int supply_id = su.getSupply_id();
			String image0 = su.getSupply_image();
			String supply_name = su.getSupply_name();
			String supply_address = su.getSupply_address();
			String supply_ph = su.getSupply_ph();
			String supply_email = su.getSupply_email();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("key", supply_id);
			jsonObject.put("image", image0);
			jsonObject.put("supply_name", supply_name);
			jsonObject.put("supply_address", supply_address);
			jsonObject.put("supply_ph", supply_ph);
			jsonObject.put("supply_email", supply_email);

			jsonArray.put(jsonObject);
		}
		return jsonArray;
	}

	// 顯示: 各(上架中)商品之 名稱,原價,葷素
	// 不顯示: id,折扣模式,折扣期間
	// script計算: 實價
	// script判斷: 是否於折扣期間
	private static JSONArray view_sales(ArrayList<Sales> s) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < s.size(); i++) {
			Sales sa = s.get(i);

			if (sa.getCommodity_onsale().equals("Y")) {
				JSONObject jsonObject = new JSONObject();

				jsonObject.put("commodity_id", sa.getCommodity_id());
				jsonObject.put("image", sa.getCommodity_image());
				jsonObject.put("commodity_name", sa.getCommodity_name());
				jsonObject.put("commodity_price", sa.getCommodity_price());
				jsonObject.put("commodity_discount_type", sa.getCommodity_discount_type());
				jsonObject.put("commodity_discount_price", sa.getCommodity_discount_number());
				jsonObject.put("discount_timeini", sa.getDiscount_timeini());
				jsonObject.put("discount_timefini", sa.getDiscount_timefini());

				String vegan = "葷";
				if (sa.getVegan().equals("V")) {
					vegan = "素";
				}
				jsonObject.put("vegan", vegan);

				jsonArray.put(jsonObject);
			}
		}
		return jsonArray;
	}

	// 生成字串-產生購物清單
	// 更改字串-加入購物清單
	private static String create_or_add_list(String shoppinglist, int commodity_id, int order_quantity) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String order_date = ft.format(new Date());

		JSONArray jsonArray;

		if (shoppinglist != null) {
			jsonArray = new JSONArray(shoppinglist);

			boolean flag = true;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if (jsonObject.getInt("commodity_id") == commodity_id) {//如果加入重複的商品就跑這裡
					int quantity_original = jsonObject.getInt("quantity");
					jsonObject.put("quantity", quantity_original + order_quantity);
					jsonObject.put("date", order_date);
					flag = false;
					break;
				}
			}

			if (flag) {//加入的商品不一樣就跑這裡
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("key", jsonArray.length() + 1);
				jsonObject.put("commodity_id", commodity_id);
				jsonObject.put("quantity", order_quantity);
				jsonObject.put("date", order_date);
				jsonArray.put(jsonObject);
			}
		} else {//購物車清單一開始沒有東西就跑這裡
			jsonArray = new JSONArray();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("key", 1);
			jsonObject.put("commodity_id", commodity_id);
			jsonObject.put("quantity", order_quantity);
			jsonObject.put("date", order_date);
			jsonArray.put(jsonObject);
		}

		return jsonArray.toString();
	}

	// 更改字串-更改特定購物清單之數量
	public static String update_quantity(int key, int order_quantity, Customer c) {
		String list = c.getShopping_list();

		JSONArray jsonArray = new JSONArray(list);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if (key == jsonObject.getInt("key")) {
				jsonObject.put("quantity", order_quantity);
				break;
			}
		}

		String jsonStr = jsonArray.toString();
		return jsonStr;
	}

	// 更改字串-刪除特定購物之清單
	public static String delete_list(int key, Customer c) {
		String list = c.getShopping_list();

		JSONArray jsonArray = new JSONArray(list);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if (key == jsonObject.getInt("key")) {
				jsonArray.remove(i);
				break;
			}
		}

		String jsonStr = jsonArray.toString();
		return jsonStr;
	}

}
