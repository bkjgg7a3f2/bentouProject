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

//@WebFilter(urlPatterns = "/shop/*" , initParams = { @WebInitParam(name = "redirectURL", value = "/cstmr/") })
@Order(value = 3)
public class ShopLoginStatusFilter implements Filter {
	private WebApplicationContext context;
	private FilterConfig filterConfig;
	private String redirectURL;
	
	@Override
	public void init(FilterConfig config) throws ServletException {

		this.filterConfig = config;
		ServletContext contex = config.getServletContext();
		String urlStr = contex.getContextPath();
		redirectURL = urlStr + filterConfig.getInitParameter("redirectURL");
		System.out.println("CheckLoginStatusFilter redirect url:" + redirectURL);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 參數設定
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String memberType = (String) session.getAttribute("memberType");
		
		//取得執行filter頁面
		String url = req.getServletPath();
		System.out.println("URL is in CheckLoginStatusFilter = " + url);
		
		//會員型別不符時重新入該歡迎頁面
		if (memberType.equals("customer")) {
			resp.sendRedirect(redirectURL);
			return;
		}
		;
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		((ConfigurableApplicationContext) context).close();
	}

}
