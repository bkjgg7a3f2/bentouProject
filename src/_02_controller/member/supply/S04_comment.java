package _02_controller.member.supply;

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
import _01_model._06_comment.Comment;
import _01_model._06_comment.CommentService;

@Controller
@RequestMapping(value = "/shop")
public class S04_comment {
	@Autowired
	private HttpServletRequest request;

	private CustomerService cservice;
	private CommentService coservice;

	@Autowired
	private S04_comment(CustomerService cservice, CommentService coservice) {
		this.cservice = cservice;
		this.coservice = coservice;
	}

	// 顯示顧客回饋(廠商自己的)
	@RequestMapping(value = "/comment", method = RequestMethod.GET)
	public String show_self_comment() {
		HttpSession session = request.getSession();
		int supply_id = (int) session.getAttribute("supply_id");

		Comment co = new Comment(supply_id);
		ArrayList<Comment> c = coservice.selectAllbySupply_id(co);

		JSONArray json_cu = new JSONArray();
		JSONArray json_su = new JSONArray();
		for (int r = 0; r < c.size(); r++) {
			Comment com = c.get(r);
			int cstmr_fraction = com.getCstmr_fraction();
			int cstmr_id = com.getComment_cstmr_id();

			String comment_id = com.getComment_id();
			String cstmr_name = cservice.selectOne(cstmr_id).getCstmr_name();
			String cstmr_evaluation = com.getCstmr_evaluation();

			String supply_evaluation = com.getSupply_evaluation();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String msgTime = sdf.format(com.getMsgTime());

			JSONObject jsonObject_cu = new JSONObject();
			JSONObject jsonObject_su = new JSONObject();
			jsonObject_cu.put("comment_id", comment_id);// 不顯示,設定至寫回饋的controller
			jsonObject_cu.put("cstmr_fraction", cstmr_fraction);
			jsonObject_cu.put("cstmr_name", cstmr_name);
			jsonObject_cu.put("cstmr_evaluation", cstmr_evaluation);
			jsonObject_cu.put("msgTime", msgTime);

			jsonObject_su.put("supply_evaluation", supply_evaluation);
			if (com.getReplyTime() != null) {
				jsonObject_su.put("replyTime", sdf.format(com.getReplyTime()));
			}
			
			json_cu.put(jsonObject_cu);
			json_su.put(jsonObject_su);
		}

		request.setAttribute("JSON_viewEvaluation_cu", json_cu);
		request.setAttribute("JSON_viewEvaluation_su", json_su);

		return "03ShopPages/04ShopCommentPages/shopCommentReadPage";
	}

	// 輸入顧客回饋(選定評論)
	@RequestMapping(value = "/comment/feedback/{comment_id}", method = RequestMethod.POST)
	public String write_feedback(@PathVariable String comment_id,
			@RequestParam(name = "supply_evaluation") String supply_evaluation) {
		if (supply_evaluation.length() == 0) {
			request.setAttribute("msg", "請輸入留言");
			return "03ShopPages/04ShopCommentPages/shopCommentReadPagebody";
		}

		Comment com = coservice.selectOne(comment_id);
		Date date = new Date();

		com.setSupply_evaluation(supply_evaluation);
		com.setReplyTime(date);
		coservice.update(com);
		request.setAttribute("msg", "回覆成功");

		return "redirect: " + request.getContextPath() + "/shop/comment";
	}

}
