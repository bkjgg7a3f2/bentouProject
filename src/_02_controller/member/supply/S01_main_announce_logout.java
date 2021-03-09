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

import _01_model._02_supply.Supply;
import _01_model._02_supply.SupplyService;
import _01_model._03_sales.Sales;
import _01_model._03_sales.SalesService;
import _01_model._05_orders_detail.Details;
import _01_model._05_orders_detail.DetailsService;
import _01_model._06_comment.Comment;
import _01_model._06_comment.CommentService;
import _01_model._07_billboard.Billboard;
import _01_model._07_billboard.BillboardService;

@Controller
@RequestMapping(value = "/shop")
public class S01_main_announce_logout {
	@Autowired
	private HttpServletRequest request;

	private SupplyService suservice;
	private SalesService saservice;
	private DetailsService dservice;
	private CommentService coservice;
	private BillboardService bService;

	@Autowired
	private S01_main_announce_logout(SupplyService suservice, SalesService saservice, DetailsService dservice,
			CommentService coservice, BillboardService bService) {
		this.suservice = suservice;
		this.saservice = saservice;
		this.dservice = dservice;
		this.coservice = coservice;
		this.bService = bService;
	}

	// 首頁(純轉移)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	private String home() {
		HttpSession session = request.getSession();
		int supply_id = (int) session.getAttribute("supply_id");

		// 自家商品熱銷排名
		ArrayList<Details> de = dservice.selectAllbySupply_id(supply_id);
		ArrayList<String> sales = new ArrayList<String>();
		Map<String, Integer> mapsale = new HashMap<String, Integer>();

		// 紀錄同一商品之總數量map
		for (int j = 0; j < de.size(); j++) {
			Details det = de.get(j);
			String name = det.getCommodity_name();
			int num = det.getOrder_quantity();
			if (det.getOrder_confirm().equals("Agree")) {
				if (!mapsale.containsKey(name)) {
					sales.add(name);
					mapsale.put(name, num);
				} else {
					int onum = mapsale.get(name);
					mapsale.put(name, onum + num);
				}
			}
		}

		// 移入雙陣列 比較數量多寡(多至少)
		String sales_name[] = new String[sales.size()];
		int sales_num[] = new int[sales.size()];

		for (int k = 0; k < sales.size(); k++) {
			String nnnn = sales.get(k);
			int mmmm = mapsale.get(nnnn);
			sales_name[k] = nnnn;
			sales_num[k] = mmmm;
		}

		while (true) {
			int count = 0;
			for (int i = 0; i < sales.size() - 1; i++) {
				int x;
				String y;
				if (sales_num[i] < sales_num[i + 1]) {
					x = sales_num[i];
					sales_num[i] = sales_num[i + 1];
					sales_num[i + 1] = x;

					y = sales_name[i];
					sales_name[i] = sales_name[i + 1];
					sales_name[i + 1] = y;
					count++;
				}
			}
			if (count == 0) {
				break;// 全按順序 跳出迴圈
			}
		}

		// 取出廠商之所有上架商品
		ArrayList<Sales> sa = saservice.selectAllbySupplyId(new Sales(supply_id));
		Map<String, String> name_image = new HashMap<String, String>();
		for (int sd = 0; sd < sa.size(); sd++) {
			Sales sc = sa.get(sd);
			if (sc.getCommodity_onsale().equals("Y")) {
				name_image.put(sc.getCommodity_name(), sc.getCommodity_image());
			}
		}

		// 取出數量前3多 且存在於上架商品中
		Map salesmap = new HashMap();
		int ac = 1;
		for (int h = 0; h < sales.size(); h++) {
			if (name_image.containsKey(sales_name[h])) {
				salesmap.put("name" + ac, sales_name[h]);
				salesmap.put("num" + ac, sales_num[h]);
				salesmap.put("image" + ac, name_image.get(sales_name[h]));

				ac++;
			}
			if (ac > 3) {
				break;
			}
		}

		request.setAttribute("best_sales", salesmap);

		// 商家評價排名(取前3)
		ArrayList<Supply> s = suservice.selectall();

		int id[] = new int[s.size()];
		double score[] = new double[s.size()];

		for (int i = 0; i < s.size(); i++) {
			Supply sup = s.get(i);
			int sid = sup.getSupply_id();

			Comment com = new Comment(sid);
			ArrayList<Comment> c = coservice.selectAllbySupply_id(com);

			Double fraction_sum = 0.0;
			for (int j = 0; j < c.size(); j++) {
				Comment bb1 = c.get(j);
				int fraction = bb1.getCstmr_fraction();
				fraction_sum = fraction_sum + fraction;
			}
			Double d = fraction_sum / c.size();

			double fractionaverage = Math.round(d * 10.0) / 10.0;

			id[i] = sid;
			score[i] = fractionaverage;
		}

		while (true) {
			int count = 0;
			for (int i = 0; i < s.size() - 1; i++) {
				double x;
				int y;
				if (score[i] < score[i + 1]) {
					x = score[i];
					score[i] = score[i + 1];
					score[i + 1] = x;

					y = id[i];
					id[i] = id[i + 1];
					id[i + 1] = y;
					count++;
				}
			}

			if (count == 0) {
				break;// 全按順序 跳出迴圈
			}
		}

		int num = 3;
		if (s.size() < num) {
			num = s.size();
		}

		Map map = new HashMap();
		for (int x = 0; x < num; x++) {
			map.put("supply_name" + (x + 1), suservice.selectOne(id[x]).getSupply_name());
			map.put("image" + (x + 1), suservice.selectOne(id[x]).getSupply_image());
			map.put("score" + (x + 1), score[x]);
		}

		for (int y = num; y < 3; y++) {
			map.put("supply_name" + (y + 1), "無商家");
			map.put("image" + (y + 1), "default.jpg");
			map.put("score" + (y + 1), "無評");
		}

		request.setAttribute("best_shop", map);

		return "03ShopPages/00MainShop/shopWelcomeLogin";
	}

	// 顯示公告
	@RequestMapping(value = "/announcement", method = RequestMethod.GET)
	public String show_announcement() {
		ArrayList<Billboard> bill = bService.selectAll();
		JSONArray jsonArray = new JSONArray();
		for (int y = 0; y < bill.size(); y++) {
			Billboard bbb = bill.get(y);
			String st = bbb.getStatus();
			if (st.equals("on")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("key", bbb.getBillboard_id());
				jsonObject.put("announcement_title", bbb.getTitle());
				jsonObject.put("announcement_picture1", bbb.getPicture1());
				jsonObject.put("announcement_time", sdf.format(bbb.getTime()));
				jsonArray.put(jsonObject);
			}
		}
		request.setAttribute("JSON_Announcement", jsonArray);
		return "00MainPage/AnnouncementSelectStatus_ajax";
	}

	// 顯示單一選定公告
	@RequestMapping(value = "/announcement/{key}", method = RequestMethod.GET)
	public String Announcement_SelectOne(@PathVariable String key) {
		try {
			int id = Integer.parseInt(key);
			Billboard bi = bService.selectOne(id);

			if (bi == null) {
				request.setAttribute("error", "查無結果");
				return "00MainPage/WarningError";
			}

			if (bi.getStatus().equals("on")) {

				SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date dd = bi.getTime();

				String time = ff.format(dd);
				Map<String, String> bill_select = new HashMap<String, String>();
				bill_select.put("announcement_title", bi.getTitle());
				bill_select.put("announcement_picture1", bi.getPicture1());
				bill_select.put("announcement_content", bi.getContent());
				bill_select.put("announcement_picture2", bi.getPicture2());
				bill_select.put("announcement_picture3", bi.getPicture3());
				bill_select.put("announcement_time", time);

				request.setAttribute("MAP_Announcement", bill_select);

				return "00MainPage/AnnouncementSelectOne_ajax";
			} else {
				request.setAttribute("error", "查無結果");
				return "00MainPage/WarningError";
			}
		} catch (NumberFormatException e) {
			request.setAttribute("error", "查無結果");
			return "00MainPage/WarningError";
		}
	}

	// 登出 POST
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String processAction() {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect: " + request.getContextPath() + "/welcome/";
	}

}
