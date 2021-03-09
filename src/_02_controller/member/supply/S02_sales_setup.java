package _02_controller.member.supply;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import _01_model._02_supply.Supply;
import _01_model._02_supply.SupplyService;
import _01_model._03_sales.Sales;
import _01_model._03_sales.SalesService;

@Controller
@RequestMapping(value = "/shop")
public class S02_sales_setup {
	@Autowired
	private HttpServletRequest request;

	private SupplyService suservice;
	private SalesService saservice;

	@Autowired
	private S02_sales_setup(SupplyService suservice, SalesService saservice) {
		this.suservice = suservice;
		this.saservice = saservice;
	}

	// 顯示自家商品列表(JSON型式)
	@RequestMapping(value = "/sales", method = RequestMethod.GET)
	private String select_sales() {
		HttpSession session = request.getSession();

		int supply_id = (int) session.getAttribute("supply_id");

		ArrayList<Sales> s = saservice.selectAllbySupplyId(new Sales(supply_id));
		JSONArray jsonArray = S02_sales_setup.view_sales(s);

		request.setAttribute("JSON_sales_supply_id", jsonArray);

		return "03ShopPages/02ShopItems/shopItemSearch";
	}

	// 顯示自家選定之商品明細(Map型式)
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/sales/{key}", method = RequestMethod.GET)
	private String sales_search(@PathVariable String key) {
		HttpSession session = request.getSession();
		int supply_id = (int) session.getAttribute("supply_id");

		try {
			int sales_num = Integer.parseInt(key);
			if (sales_num == 0) {
				return "redirect: " + request.getContextPath() + "/shop/sales";
			}

			Map<Integer, Integer> map_id = saservice.map_sales(new Sales(supply_id));
			if (!map_id.containsKey(sales_num)) {
				request.setAttribute("error", "查無結果");
				return "00MainPage/WarningError";
			}

			Sales sale = saservice.selectOne(map_id.get(sales_num));
			Map sales_data = S02_sales_setup.view_form(sale);
			request.setAttribute("Map_sales_form", sales_data);
			request.setAttribute("image", sale.getCommodity_image());
			request.setAttribute("key", sales_num);
			return "03ShopPages/02ShopItems/shopItemInfo";
		} catch (NumberFormatException e) {
			request.setAttribute("error", "查無結果");
			return "00MainPage/WarningError";
		}
	}

	// 轉移至新增頁面 或 呼叫修改選定商品之頁面(Map型式)
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/sales/form/{key}", method = RequestMethod.POST)
	private String view_insert_or_update(@PathVariable int key) {
		HttpSession session = request.getSession();
		int supply_id = (int) session.getAttribute("supply_id");

		if (key != 0) {
			Map<Integer, Integer> map_id = saservice.map_sales(new Sales(supply_id));
			Sales sal = saservice.selectOne(map_id.get(key));

			Map sales = S02_sales_setup.view_form(sal);

			request.setAttribute("display_add", "none");
			request.setAttribute("Map_sales_form", sales);
			request.setAttribute("title", "修改");
			request.setAttribute("image", sal.getCommodity_image());
		} else {
			Map<String, String> sales = new HashMap<String, String>();
			sales.put("discount_type_2", "false");
			sales.put("discount_type_3", "false");

			request.setAttribute("display_update", "none");
			request.setAttribute("Map_sales_form", sales);
			request.setAttribute("title", "新增");
			request.setAttribute("image", "noPic.png");
		}
		request.setAttribute("key", key);
		return "03ShopPages/02ShopItems/shopItemChange";
	}

	// 新增商品 或 修改選定商品之資料
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/sales/form_fail/{key}", method = RequestMethod.POST)
	private String insert_or_update(@PathVariable int key, @RequestParam(name = "commodity_name") String name,
			@RequestParam(name = "commodity_price") String price,
			@RequestParam(name = "commodity_discount_type") int discount_type,
			@RequestParam(name = "commodity_discount_number_2", required = false) String discount_number_2,
			@RequestParam(name = "commodity_discount_number_3", required = false) String discount_number_3,
			@RequestParam(name = "Vegan") String vegan,
			@RequestParam(name = "discount_timeini", required = false) String timeini,
			@RequestParam(name = "discount_timefini", required = false) String timefini,
			@RequestParam(name = "commodity_onsale") String onsale,
			@RequestParam(name = "myFiles") MultipartFile multipartfile)
			throws ParseException, IllegalStateException, IOException {
		HttpSession session = request.getSession();
		int supply_id = (int) session.getAttribute("supply_id");

		String errors = S02_sales_setup.check_text(name, price, discount_type, discount_number_2, discount_number_3,
				timeini, timefini);

		String image_from_bean = "noPic.png";
		boolean name_check = true;
		Map<Integer, Integer> map_id = saservice.map_sales(new Sales(supply_id));
		if (key != 0) {
			Sales sale = saservice.selectOne(map_id.get(key));
			image_from_bean = sale.getCommodity_image();
			if (name.equals(sale.getCommodity_name())) {
				name_check = false;
			}
		}
		if (name_check) {
			ArrayList<Sales> s = saservice.selectAllbySupplyId(new Sales(supply_id));

			for (int j = 0; j < s.size(); j++) {
				String sales_name = s.get(j).getCommodity_name();
				if (name.equals(sales_name)) {
					errors = "商品名稱不可與既有商品重複";
					break;
				}
			}
		}

		if (errors != null) {
			Map sales = new HashMap();
			sales.put("key", key);
			sales.put("name", name);
			sales.put("price", price);

			switch (discount_type) {
			case 1:
				sales.put("discount_type_2", "false");
				sales.put("discount_type_3", "false");
				break;
			case 2:
				sales.put("discount_type_2", "true");
				sales.put("discount_type_3", "false");
				break;
			case 3:
				sales.put("discount_type_2", "false");
				sales.put("discount_type_3", "true");
				break;
			}

			sales.put("discount_number_2", discount_number_2);
			sales.put("discount_number_3", discount_number_3);

			if (vegan.equals("V")) {
				sales.put("Vegan_V", "checked");
			}

			sales.put("timeini", timeini);
			sales.put("timefini", timefini);

			if (onsale.equals("N")) {
				sales.put("onsale_no", "checked");
			}

			request.setAttribute("Map_sales_form", sales);
			request.setAttribute("errormsg", errors);
			request.setAttribute("image", image_from_bean);

			if (key == 0) {
				request.setAttribute("title", "新增");
				request.setAttribute("display_update", "none");
			} else {
				request.setAttribute("title", "修改");
				request.setAttribute("display_add", "none");
			}

			return "03ShopPages/02ShopItems/shopItemChange";
		}

		Sales sal = S02_sales_setup.set_supply_bean(supply_id, name, price, discount_type, discount_number_2,
				discount_number_3, timeini, timefini, vegan, onsale, multipartfile, image_from_bean);

		if (key == 0) {
			saservice.insert(sal);
			request.setAttribute("insertSuccess", "商品新增成功");

			return "redirect: " + request.getContextPath() + "/shop/sales";
		} else {
			sal.setCommodity_id(map_id.get(key));
			saservice.update(sal);
			request.setAttribute("updateSuccess", "商品更新成功");

			return "redirect: " + request.getContextPath() + "/shop/sales/" + key;
		}
	}

	// 刪除選定商品之資料
	@RequestMapping(value = "/sales/delete", method = RequestMethod.POST)
	private String delete() {
		HttpSession session = request.getSession();
		int supply_id = (int) session.getAttribute("supply_id");

		int key = (int) session.getAttribute("key");

		Map<Integer, Integer> map_id = saservice.map_sales(new Sales(supply_id));
		int commodity_id = map_id.get(key);
		saservice.delete(commodity_id);
		return "redirect: " + request.getContextPath() + "/supply/sales";
	}

	// 預覽消費者頁面(JSON型式)
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	private String show_sales() {
		HttpSession session = request.getSession();
		int supply_id = (int) session.getAttribute("supply_id");
		Supply sup = suservice.selectOne(supply_id);

		ArrayList<Sales> s = saservice.selectAllbySupplyId(new Sales(supply_id));

		if (s.size() == 0) {
			request.setAttribute("error", "該商家尚未上架任何商品");
			return "00MainPage/WarningError";
		}

		JSONArray jsonArray = S02_sales_setup.cstmr_view_sales(s);

		request.setAttribute("JSON_supply_sales", jsonArray);

		request.setAttribute("sum", 0);
		request.setAttribute("limit", sup.getLimit());

		request.setAttribute("supply_image", sup.getSupply_image());
		request.setAttribute("key", supply_id);
		return "03ShopPages/02ShopItems/shopItemSearch_ajax";
	}

	// 以下為上述Controller內之使用方法
	// 顯示: 各商品之 名稱,原價,葷素,折扣期間,上架狀態
	// 不顯示: key-設定按鈕,controller用
	// script計算: 實價
	// script判斷: 是否於折扣期間
	private static JSONArray view_sales(ArrayList<Sales> s) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < s.size(); i++) {
			Sales sal = s.get(i);

			String vegan = sal.getVegan();
			String vegan_alter = "未知";
			if (vegan.equals("M")) {
				vegan_alter = "葷";
			} else if (vegan.equals("V")) {
				vegan_alter = "素";
			}

			String onsale = sal.getCommodity_onsale();
			String onsale_alter = "未知";
			;
			if (onsale.equals("Y")) {
				onsale_alter = "是";
			} else if (onsale.equals("N")) {
				onsale_alter = "否";
			}

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("key", i + 1);
			jsonObject.put("image", sal.getCommodity_image());
			jsonObject.put("name", sal.getCommodity_name());
			jsonObject.put("price", sal.getCommodity_price());
			jsonObject.put("vegan", vegan_alter);
			jsonObject.put("onsale", onsale_alter);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (sal.getDiscount_timeini() != null) {
				jsonObject.put("timeini", sdf.format(sal.getDiscount_timeini()));
			}

			if (sal.getDiscount_timefini() != null) {
				jsonObject.put("timefini", sdf.format(sal.getDiscount_timefini()));
			}

			if (sal.getUpdate_date() != null) {
				jsonObject.put("update_date", sdf.format(sal.getUpdate_date()));
			}
			jsonArray.put(jsonObject);
		}
		return jsonArray;
	}

	// 設定表單欄位資訊
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map view_form(Sales sal) {
		Map sales = new HashMap();

		sales.put("name", sal.getCommodity_name());
		sales.put("price", sal.getCommodity_price());

		switch (sal.getCommodity_discount_type()) {
		case 1:
			sales.put("discount_type_2", "false");
			sales.put("discount_type_3", "false");
			sales.put("discount_number_2", 0);
			sales.put("discount_number_3", 0);
			break;
		case 2:
			sales.put("discount_type_2", "true");
			sales.put("discount_type_3", "false");
			sales.put("discount_number_2", sal.getCommodity_discount_number());
			sales.put("discount_number_3", 0);
			break;
		case 3:
			sales.put("discount_type_2", "false");
			sales.put("discount_type_3", "true");
			sales.put("discount_number_2", 0);
			sales.put("discount_number_3", sal.getCommodity_discount_number());
			break;
		}

		if (sal.getVegan().equals("V")) {
			sales.put("Vegan_V", "checked");
		}

		if (sal.getDiscount_timeini() != null && sal.getDiscount_timefini() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sales.put("timeini", sdf.format(sal.getDiscount_timeini()));
			sales.put("timefini", sdf.format(sal.getDiscount_timefini()));
		}

		if (sal.getCommodity_onsale().equals("N")) {
			sales.put("onsale_no", "checked");
		}

		return sales;
	}

	// 判斷商品設定是否有空值
	private static String check_text(String name, String price, int discount_type, String discount_number_2,
			String discount_number_3, String timeini, String timefini) throws ParseException {
		String errors = null;
		if (discount_type == 2 || discount_type == 3) {
			if (timeini != null && timeini.length() != 0 && timefini != null && timefini.length() != 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date_timeini = sdf.parse(timeini);
				Date date_timefini = sdf.parse(timefini);
				if (date_timeini.after(date_timefini)) {
					errors = "折扣結束時間不可早於起始時間";
				}
			} else {
				errors = "請輸入折扣開始與結束時間";
			}
		}

		if (name == null || name.length() == 0 || price == null || price.length() == 0) {
			errors = "商品名稱與定價不可空白";
			return errors;
		}

		if (!price.matches("[\\d]+") || !price.matches("\\+?[1-9][0-9]*")) {
			errors = "商品定價請輸入正整數";
			return errors;
		}

		if (discount_type == 2) {
			if (discount_number_2 == null || discount_number_2.length() == 0 || !discount_number_2.matches("[0-9]+")
					|| !discount_number_2.matches("[1-9][0-9]?")) {
				errors = "打折數字請輸入小於100之正整數";
			}
		}

		if (discount_type == 3) {
			if (discount_number_3 != null && discount_number_3.length() != 0 && discount_number_3.matches("[0-9]+")
					&& discount_number_3.matches("\\+?[1-9][0-9]*")) {
				int intprice = Integer.valueOf(price);
				int intdiscount_number_3 = Integer.valueOf(discount_number_3);
				if (intdiscount_number_3 >= intprice) {
					errors = "降價數字請輸入低於商品定價的正整數";
				}
			}
		}

		return errors;
	}

	// 設定商品bean + 存圖片
	private static Sales set_supply_bean(int supply_id, String name, String price, int discount_type,
			String discount_number_2, String discount_number_3, String timeini, String timefini, String vegan,
			String onsale, MultipartFile multipartfile, String image_from_bean)
			throws IllegalStateException, IOException, ParseException {
		Sales sal = new Sales();

		sal.setSupply_id(supply_id);
		sal.setCommodity_name(name);

		int commodityPrice = Integer.valueOf(price);
		sal.setCommodity_price(commodityPrice);

		switch (discount_type) {
		case 1:
			sal.setCommodity_discount_type(discount_type);
			sal.setCommodity_discount_number(0);
			break;
		case 2:
			sal.setCommodity_discount_type(discount_type);
			int commodityDiscountNumber2 = Integer.valueOf(discount_number_2);
			sal.setCommodity_discount_number(commodityDiscountNumber2);
			break;
		case 3:
			sal.setCommodity_discount_type(discount_type);
			int commodityDiscountNumber3 = Integer.valueOf(discount_number_3);
			sal.setCommodity_discount_number(commodityDiscountNumber3);
			break;
		}

		if (discount_type == 2 || discount_type == 3) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dateTimeini = sdf.parse(timeini);
			sal.setDiscount_timeini(dateTimeini);
			Date dateTimefini = sdf.parse(timefini);
			sal.setDiscount_timefini(dateTimefini);
		}

		sal.setVegan(vegan);
		sal.setCommodity_onsale(onsale);

		Date date = new Date();
		sal.setUpdate_date(date);

		if (!multipartfile.isEmpty()) {
			String fileName = multipartfile.getOriginalFilename();
			String ext = FilenameUtils.getExtension(fileName);

			String Path = "D:/DataSource/Presentation/Presentation/WebContent/WEB-INF/resources/images/salesPics/";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
			String image_name = supply_id + "_" + name + "_" + sdf.format(new Date()) + "." + ext;

			// 取得副檔名
			String savePath = Path + image_name;
			String originalPath = Path + image_from_bean;
			if (ext.equals("bmp") || ext.equals("jpg") || ext.equals("jpeg") || ext.equals("gif")
					|| ext.equals("png")) {
				if (fileName != null && fileName.length() != 0) {
					File file = new File(originalPath);
					if (file.exists()) {
						file.delete();
					}
					
					File newfile = new File(savePath);
					multipartfile.transferTo(newfile);
					
					sal.setCommodity_image(image_name);
				}
			}
		} else {
			sal.setCommodity_image(image_from_bean);
		}
		return sal;
	}

	// 不顯示: id,折扣模式,折扣期間
	// script計算: 實價
	// script判斷: 是否於折扣期間
	private static JSONArray cstmr_view_sales(ArrayList<Sales> s) {
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
}