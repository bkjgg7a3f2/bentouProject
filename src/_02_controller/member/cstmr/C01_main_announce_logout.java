package _02_controller.member.cstmr;

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
import _01_model._06_comment.Comment;
import _01_model._06_comment.CommentService;
import _01_model._07_billboard.Billboard;
import _01_model._07_billboard.BillboardService;

@Controller
@RequestMapping(value = "/cstmr")
public class C01_main_announce_logout {
	@Autowired
	private HttpServletRequest request;

	private SupplyService supplyService;
	private CommentService commentService;
	private BillboardService billboardService;

	@Autowired
	private C01_main_announce_logout(SupplyService suservice, CommentService coservice, BillboardService bService) {
		this.supplyService = suservice;
		this.commentService = coservice;
		this.billboardService = bService;
	}

	// 首頁(顯示推薦商家)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	private String home() {
		// 商家評價排名
		ArrayList<Supply> s = supplyService.selectall();

		int id[] = new int[s.size()];
		double score[] = new double[s.size()];

		for (int i = 0; i < s.size(); i++) {//總結這個迴圈就是算每項商品的平均分數
			Supply sup = s.get(i);
			int supply_id = sup.getSupply_id();

			Comment com = new Comment(supply_id);
			ArrayList<Comment> c = commentService.selectAllbySupply_id(com);//查出某某商品的所有評論

			Double fraction_sum = 0.0;
			for (int j = 0; j < c.size(); j++) {
				Comment bb1 = c.get(j);
				int fraction = bb1.getCstmr_fraction();
				fraction_sum = fraction_sum + fraction;//每則評論的分數加總
			}
			Double d = fraction_sum / c.size();//全部評論的平均分數

			double fractionaverage = Math.round(d * 10.0) / 10.0;//四捨五入

			id[i] = supply_id;//把每個商品ID放進這個陣列
			score[i] = fractionaverage;//把每個商品的平均分數放進這個陣列
		}

		while (true) {
			int count = 0;
			for (int i = 0; i < s.size() - 1; i++) {//簡單來說這裡就是將平均分數由大到小排序
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

		int num = 6;
		if (s.size() < num) {
			num = s.size();
		}

		Map map = new HashMap();
		for (int x = 0; x < num; x++) {
			map.put("key" + (x + 1), id[x]);
			map.put("supply_name" + (x + 1), supplyService.selectOne(id[x]).getSupply_name());
			map.put("image" + (x + 1), supplyService.selectOne(id[x]).getSupply_image());
			map.put("score" + (x + 1), score[x]);
		}

		for (int y = num; y < 6; y++) {
			map.put("key" + (y + 1), "null");
			map.put("supply_name" + (y + 1), "無商家");
			map.put("image" + (y + 1), "default.jpg");
			map.put("score" + (y + 1), "無評");
		}

		request.setAttribute("best", map);

		return "02CustomerPages/00MainCust/custWelcomeLogin";
	}

	// 顯示公告
	@RequestMapping(value = "/announcement", method = RequestMethod.GET)
	public String show_announcement() {
		ArrayList<Billboard> bill = billboardService.selectAll();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < bill.size(); i++) {
			Billboard bbb = bill.get(i);
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
	public String announcement_selectOne(@PathVariable String key) {
		try {
			int id = Integer.parseInt(key);
			Billboard bi = billboardService.selectOne(id);

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
	public String logout() {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect: " + request.getContextPath() + "/welcome/";
	}

}
