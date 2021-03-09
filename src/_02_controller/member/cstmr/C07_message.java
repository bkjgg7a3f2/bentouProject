package _02_controller.member.cstmr;

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

import _01_model._02_supply.Supply;
import _01_model._02_supply.SupplyService;
import _01_model._04_orders.Orders;
import _01_model._04_orders.OrdersService;
import _01_model._08_message.MsgBean;
import _01_model._08_message.MsgService;

@Controller
@RequestMapping(path = "/cstmr")
public class C07_message {
	@Autowired
	private HttpServletRequest request;

	private SupplyService suservice;
	private OrdersService oservice;
	private MsgService msgservice;

	@Autowired
	private C07_message(SupplyService suservice, OrdersService oservice, MsgService msgservice) {
		this.oservice = oservice;
		this.msgservice = msgservice;
		this.suservice = suservice;
	}

	// 轉移即時聊天頁面(左側: 訂單之所有廠商(不重複))
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(path = "/message", method = RequestMethod.GET)
	private String main_supply_list() {
		HttpSession session = request.getSession();
		int cstmr_id = (int) session.getAttribute("cstmr_id");

		ArrayList<Orders> o = oservice.selectAll_cstmr_view(cstmr_id);
		Set set_id = new LinkedHashSet();
		for (int i = 0; i < o.size(); i++) {
			Orders od = o.get(i);
			int supply_id = od.getOrder_supply_id();
			set_id.add(supply_id);
		}

		Iterator a = set_id.iterator();
		JSONArray jsonarray = new JSONArray();
		while (a.hasNext()) {
			Integer id = (Integer) a.next();
			Supply sup = suservice.selectOne(id);
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("id", id);
			jsonobject.put("image", sup.getSupply_image());
			jsonobject.put("name", sup.getSupply_name());
			jsonarray.put(jsonobject);
		}

		request.setAttribute("JSON_supply_list", jsonarray);
		return "02CustomerPages/04MsgPages/custMsgPage";
	}

	// 呼叫選定廠商聊天頁面
	@RequestMapping(path = "/message_list/{supply_id}", method = RequestMethod.GET)
	private String show_messages(@PathVariable int supply_id) {
		HttpSession session = request.getSession();
		int cstmr_id = (int) session.getAttribute("cstmr_id");

		Supply sup = suservice.selectOne(supply_id);
		String image = sup.getSupply_image();
		String name = sup.getSupply_name();

		request.setAttribute("supply_id", supply_id);// 放入hidden
		request.setAttribute("supply_image", image);
		request.setAttribute("supply_name", name);

		MsgBean msgbean = new MsgBean();
		msgbean.setCstmr_id(cstmr_id);
		msgbean.setSupply_id(supply_id);

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
		return "02CustomerPages/04MsgPages/custMsgPage_ajax";
	}

	// 對選定廠商寫入訊息
	@RequestMapping(path = "/message_write", method = RequestMethod.POST)
	public String write_message(@RequestParam(name = "supply_id") String supply_id, // hidden
			@RequestParam(name = "msg_save") String msg_save) {
		HttpSession session = request.getSession();
		int setcstmr_id = (int) session.getAttribute("cstmr_id");
		int setsupply_id = Integer.valueOf(supply_id);
		String speaker = (String) session.getAttribute("memberType");

		MsgBean msgin = new MsgBean();

		msgin.setCstmr_id(setcstmr_id);
		msgin.setSupply_id(setsupply_id);
		msgin.setTalker(speaker);
		msgin.setMsg(msg_save);
		msgin.setSendTime(new Date());

		msgservice.add(msgin);

		return "redirect: " + request.getContextPath() + "/cstmr/message_list/" + setsupply_id;// 跳至下一個controller的mapping，取信息
	}
}
