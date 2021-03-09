package _00.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import _00.cookieProcess.BenTouCookieAction;


//@WebFilter(urlPatterns = "/", initParams = { @WebInitParam(name = "redirectURL", value = "/") })
//@Order(value = 1)
public class CookieCheckFilter implements Filter {
	private WebApplicationContext context;
	private FilterConfig filterConfig;
	private String redirectURL;

	public void destroy() {
		((ConfigurableApplicationContext) context).close();
	}

	@SuppressWarnings("unused")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();
		Boolean status = Boolean.parseBoolean((String) session.getAttribute("bentou_loginstatus"));

		// 未設定登入狀態時
		if (status == null) {

			// 讀取cookie的值，存入Json物件內
			BenTouCookieAction cookieAct = new BenTouCookieAction((HttpServletRequest) request,
					(HttpServletResponse) response);
			JSONObject cookieInfo = cookieAct.read();

			// 物件存在將cookie的值寫入session
			if (((String) cookieInfo.get("memberType")).equals("customer")) {
				int cstmr_id = (int) cookieInfo.get("cstmr_id");
				String bentou_loginstatus = (String) cookieInfo.get("bentou_loginstatus");

				session.setAttribute("cstmr_id", cstmr_id);
				session.setAttribute("memberType", "customer");
				session.setAttribute("bentou_loginstatus", bentou_loginstatus);

			} else if (((String) cookieInfo.get("memberType")).equals("shop")) {
				int supply_id = (int) cookieInfo.get("supply_id");
				String bentou_loginstatus = (String) cookieInfo.get("bentou_loginstatus");
				session.setAttribute("supply_id", supply_id);
				session.setAttribute("memberType", "shop");
				session.setAttribute("bentou_loginstatus", bentou_loginstatus);
			}

		}

		;
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		this.filterConfig = fConfig;
		ServletContext contex = filterConfig.getServletContext();
		String urlStr = contex.getContextPath();
		redirectURL = urlStr + filterConfig.getInitParameter("redirectURL");
		System.out.println("CookieCheckFilter redirect url:" + redirectURL);
	}

}
