package _02_controller.member.supply;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

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
import _01_model._08_message.MsgBean;
import _01_model._08_message.MsgService;

@Controller
@RequestMapping(path = "/shop")
public class S06_message {
	@Autowired
	private HttpServletRequest request;

	private CustomerService cservice;
	private MsgService msgservice;

	@Autowired
	private S06_message(CustomerService cservice, MsgService msgservice) {
		this.cservice = cservice;
		this.msgservice = msgservice;
	}

	// 轉移即時聊天頁面
	// 左側更新: 下一個controller
	@RequestMapping(path = "/message_o", method = RequestMethod.GET)
	private String main_supply_list() {
		return "03ShopPages/05MsgPages/shopMsgPage";
	}

	// 左側更新: 發送訊息之所有消費者(不重複)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(path = "/message", method = RequestMethod.GET)
	private String search_cstmr_list() {
		HttpSession session = request.getSession();
		int supply_id = (int) session.getAttribute("supply_id");

		ArrayList<MsgBean> m = msgservice.showAllbySupply_id(supply_id);
		Set set_id = new LinkedHashSet();
		for (int i = 0; i < m.size(); i++) {
			MsgBean msg = m.get(i);
			int cstmr_id = msg.getCstmr_id();
			set_id.add(cstmr_id);
		}

		Iterator a = set_id.iterator();
		JSONArray jsonarray = new JSONArray();
		while (a.hasNext()) {
			Integer id = (Integer) a.next();
			Customer cus = cservice.selectOne(id);
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("id", id);
			jsonobject.put("image", cus.getCstmr_image());
			jsonobject.put("name", cus.getCstmr_name());
			jsonarray.put(jsonobject);
		}

		request.setAttribute("JSON_cstmr_list", jsonarray);
		return "03ShopPages/05MsgPages/shopMsgPage";
	}

	// 呼叫選定消費者聊天頁面
	@RequestMapping(path = "/message_list/{cstmr_id}", method = RequestMethod.GET)
	private String show_messages(@PathVariable int cstmr_id) {
		HttpSession session = request.getSession();
		int supply_id = (int) session.getAttribute("supply_id");

		Customer cus = cservice.selectOne(cstmr_id);
		String image = cus.getCstmr_image();
		String name = cus.getCstmr_name();

		request.setAttribute("cstmr_id", cstmr_id);// 放入hidden
		request.setAttribute("cstmr_image", image);
		request.setAttribute("cstmr_name", name);

		MsgBean msgbean = new MsgBean();
		msgbean.setSupply_id(supply_id);
		msgbean.setCstmr_id(cstmr_id);

		ArrayList<MsgBean> msg_array = msgservice.showAllbyBoth_id(msgbean);
		JSONArray jsonarray = new JSONArray();

		for (int i = 0; i < msg_array.size(); i++) {
			MsgBean user = msg_array.get(i);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", user.getMsg_id());
			jsonObject.put("speaker", user.getTalker());
			jsonObject.put("msg", user.getMsg());
			jsonObject.put("sendTime", user.getSendTime());

			jsonarray.put(jsonObject);
		}

		request.setAttribute("JSON_dialogue", jsonarray);
		return "03ShopPages/05MsgPages/shopMsgPage_ajax";
	}

	// 對選定消費者寫入訊息
	@RequestMapping(path = "/message_write", method = RequestMethod.POST)
	public String write_message(@RequestParam(name = "cstmr_id") String cstmr_id, // hidden
			@RequestParam(name = "msg_save") String msg_save) {
		HttpSession session = request.getSession();
		int setsupply_id = (int) session.getAttribute("supply_id");
		int setcstmr_id = Integer.valueOf(cstmr_id);
		String speaker = (String) session.getAttribute("memberType");

		MsgBean msgin = new MsgBean();

		msgin.setCstmr_id(setcstmr_id);
		msgin.setSupply_id(setsupply_id);
		msgin.setTalker(speaker);
		msgin.setMsg(msg_save);
		msgin.setSendTime(new Date());

		msgservice.add(msgin);

		return "redirect: " + request.getContextPath() + "/shop/message_list/" + setsupply_id;// 跳至下一個controller的mapping，取信息
	}

}
