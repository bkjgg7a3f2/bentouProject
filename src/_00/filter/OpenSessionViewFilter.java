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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@WebFilter(urlPatterns = { "/manager/*", "/welcome/*", "/cstmr/*", "/shop/*" })
@Order(value = 1)
public class OpenSessionViewFilter implements Filter {
	private SessionFactory sessionFactory;
	private WebApplicationContext context;

	private static final String sessionFactoryBeanName = "sessionFactory";

	@Override
	public void init(FilterConfig config) throws ServletException {
		ServletContext application = config.getServletContext();
		context = WebApplicationContextUtils.getWebApplicationContext(application);
		sessionFactory = (SessionFactory) context.getBean(sessionFactoryBeanName);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			Session x = sessionFactory.getCurrentSession();
			x.beginTransaction();
			System.out.println("Transaction Begin.");
			chain.doFilter(request, response);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (Exception e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			System.out.println("Transaction RollBack.");
			chain.doFilter(request, response);
		} finally {
			System.out.println("Transaction Closed.");
		}
	}

	@Override
	public void destroy() {
		((ConfigurableApplicationContext) context).close();
	}

}
