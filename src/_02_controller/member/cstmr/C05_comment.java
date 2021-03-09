package _02_controller.member.cstmr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import _01_model._02_supply.Supply;
import _01_model._02_supply.SupplyService;
import _01_model._04_orders.OrdersService;
import _01_model._06_comment.Comment;
import _01_model._06_comment.CommentService;

@Controller
@RequestMapping(value = "/cstmr")
public class C05_comment {
	@Autowired
	private HttpServletRequest request;

	private CustomerService cservice;
	private SupplyService suservice;
	private OrdersService oservice;
	private CommentService coservice;

	@Autowired
	private C05_comment(CustomerService cservice, SupplyService suservice, OrdersService oservice,
			CommentService coservice) {
		this.cservice = cservice;
		this.suservice = suservice;
		this.oservice = oservice;
		this.coservice = coservice;
	}

	// 顯示評論區(選定廠商)
	@RequestMapping(value = "/comment/{key}", method = RequestMethod.GET)
	public String show_comment(@PathVariable String key) {
		try {
			int supply_id = Integer.parseInt(key);
			Supply sup = suservice.selectOne(supply_id);
			if (sup == null) {
				request.setAttribute("warning", "查無結果");
				return "00MainPage/WarningError";
			}

			Comment co = new Comment(supply_id);
			ArrayList<Comment> c = coservice.selectAllbySupply_id(co);

			JSONArray json_cu = new JSONArray();
			JSONArray json_su = new JSONArray();
			for (int r = 0; r < c.size(); r++) {
				Comment com = c.get(r);
				int cstmr_fraction = com.getCstmr_fraction();
				int bbs_cstmr_id = com.getComment_cstmr_id();

				String cus_name = cservice.selectOne(bbs_cstmr_id).getCstmr_name();
				String cstmr_evaluation = com.getCstmr_evaluation();

				String sup_name = suservice.selectOne(supply_id).getSupply_name();
				String supply_evaluation = com.getSupply_evaluation();

				JSONObject jsonObject_cu = new JSONObject();
				JSONObject jsonObject_su = new JSONObject();
				jsonObject_cu.put("cstmr_fraction", cstmr_fraction);
				jsonObject_cu.put("cus_name", cus_name);
				jsonObject_cu.put("cstmr_evaluation", cstmr_evaluation);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				jsonObject_cu.put("msgTime", sdf.format(com.getMsgTime()));

				jsonObject_su.put("sup_name", sup_name);
				jsonObject_su.put("supply_evaluation", supply_evaluation);
				if (com.getReplyTime() != null) {
					jsonObject_su.put("replyTime", sdf.format(com.getReplyTime()));
				}

				json_cu.put(jsonObject_cu);
				json_su.put(jsonObject_su);
			}
			
			request.setAttribute("key", key);
			request.setAttribute("JSON_viewEvaluation_cu", json_cu);
			request.setAttribute("JSON_viewEvaluation_su", json_su);

			return "02CustomerPages/02Shopping/custShopCommentRead_ajax";
		} catch (NumberFormatException e) {
			request.setAttribute("warning", "查無結果");
			return "00MainPage/WarningError";
		}
	}

	// 送出評論(選定訂單)
	@RequestMapping(value = "/comment/write", method = RequestMethod.POST)
	public String write_comment(@RequestParam(name = "order_id") String order_id,
			@RequestParam(name = "cstmr_fraction") int cstmr_fraction,
			@RequestParam(name = "cstmr_evaluation") String cstmr_evaluation) {
		if (cstmr_evaluation.length() == 0) {
			request.setAttribute("order_id", order_id);
			request.setAttribute("msg", "請輸入留言");
			return "redirect: " + request.getContextPath() + "/cstmr/orders/" + order_id;
		}

		HttpSession session = request.getSession();
		int cstmr_id = (int) session.getAttribute("cstmr_id");
		int supply_id = oservice.selectOne(order_id).getOrder_supply_id();
		Date date = new Date();

		Comment co = new Comment();
		co.setComment_id(order_id);
		co.setComment_supply_id(supply_id);
		co.setComment_cstmr_id(cstmr_id);
		co.setCstmr_fraction(cstmr_fraction);
		co.setCstmr_evaluation(cstmr_evaluation);
		co.setMsgTime(date);
		coservice.add(co);

		request.setAttribute("msg", "留言成功");

		return "redirect: " + request.getContextPath() + "/cstmr/orders/" + order_id;
	}
}
