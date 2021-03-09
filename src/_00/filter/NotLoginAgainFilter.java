package _00.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.WebApplicationContext;

//@WebFilter(urlPatterns = "/welcome/*", initParams = { @WebInitParam(name = "redirectURL", value = "/") })
// 設定Filter過濾目標與重新導向頁面
@Order(value = 2)
public class NotLoginAgainFilter implements Filter {
	private WebApplicationContext context;

	private FilterConfig filterConfig;
	private String redirectURL;

	@Override
	public void init(FilterConfig config) throws ServletException {

		this.filterConfig = config;
		ServletContext contex = config.getServletContext();
		String urlStr = contex.getContextPath();
		//取得專案路徑
		redirectURL = urlStr+filterConfig.getInitParameter("redirectURL");
		//專案預設重新導向路徑設定
		System.out.println("NotLoginAgainFilter redirect url:"+redirectURL);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String url = req.getServletPath();
		System.out.println("URL is in NotLoginAgainFilter = " + url);
		HttpSession session = req.getSession();
		String redirectURL_final=redirectURL;//取得最後跳轉的url
		String memberType = (String) session.getAttribute("memberType");

			
		Boolean status = Boolean.parseBoolean((String) session.getAttribute("bentou_loginstatus"));
		if (status != null) {
			boolean statusValue = status.booleanValue();
			if (statusValue) {
				
				if (memberType.equals("customer")) {
					redirectURL_final = redirectURL+"cstmr/";
				} else if (memberType.equals("shop")) {
					redirectURL_final = redirectURL+"shop/";
				};
				System.out.println("User is logged in. Redirect to "+redirectURL_final);
				resp.sendRedirect(redirectURL_final);
				return;
			} 
		} 
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		((ConfigurableApplicationContext) context).close();
	}

}
