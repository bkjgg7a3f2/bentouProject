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

//@WebFilter(urlPatterns = { "/cstmr/*", "/shop/*" }, initParams = { @WebInitParam(name = "redirectURL", value = "/") })
@Order(value = 2)
public class CheckLoginStatusFilter implements Filter {
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

	@SuppressWarnings("unused")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String url = req.getServletPath();
		System.out.println("URL is in CheckLoginStatusFilter = " + url);
		HttpSession session = req.getSession();
		Boolean status = Boolean.parseBoolean((String) session.getAttribute("bentou_loginstatus"));

		if (status != null) {
			boolean statusValue = status.booleanValue();
			if (statusValue) {
				System.out.println("User is logged in.");
				chain.doFilter(request, response);
			} else {
				resp.sendRedirect(redirectURL);
				return;
			}
		} else {
			resp.sendRedirect(redirectURL);
			return;
		}
	}

	@Override
	public void destroy() {
		((ConfigurableApplicationContext) context).close();
	}

}
