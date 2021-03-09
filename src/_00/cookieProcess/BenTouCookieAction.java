package _00.cookieProcess;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class BenTouCookieAction {
	private HttpServletRequest request;
	private HttpServletResponse response;

	public BenTouCookieAction() {
	}
	
	public BenTouCookieAction(HttpServletRequest req,HttpServletResponse resp) {
		this.request=req;
		this.response=resp;
	}
	
	public JSONObject read() {
		response.setContentType("text/html;charset=UTF-8");
		Cookie[] cookies = request.getCookies();
		
		JSONObject member_id = new JSONObject();
		if (cookies != null) {
			boolean loginstatus=false;
			for(Cookie cook: cookies) {
				if(cook.getName()=="bentou_loginstatus") {
					loginstatus=Boolean.getBoolean(cook.getValue());
					member_id.put("loginstatus", loginstatus);
					break;
				}
			}
			
			if(loginstatus) {
				for(Cookie cook: cookies) {
					if(cook.getName().equals("cstmr_id")) {
						int cstmr_id=Integer.valueOf(cook.getValue());
						member_id.put("cstmr_id", cstmr_id);
						break;
					}else if(cook.getName().equals("supply_id")) {
						int supply_id=Integer.valueOf(cook.getValue());
						member_id.put("supply_id", supply_id);
						break;
					}
				}
			}else {
				member_id.put("loginstatus", loginstatus);
			}
		}
		
		return member_id;
	}
	public void write(String member,int id) {
		Cookie loginstatus = new Cookie("bentou_loginstatus", "true");
		loginstatus.setMaxAge(24*60*60);
		response.addCookie(loginstatus);
		
		if(member.equals("cstmr")) {
			String cstmr_id=String.valueOf(id);
			Cookie cstmr = new Cookie("cstmr_id", cstmr_id);
			cstmr.setMaxAge(24*60*60);
			response.addCookie(cstmr);
		}
		else if(member.equals("supply")) {
			String supply_id=String.valueOf(id);
			Cookie supply = new Cookie("supply_id", supply_id);
			supply.setMaxAge(24*60*60);
			response.addCookie(supply);
		}
	}
}