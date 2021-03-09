package _00.util;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:jdbc.properties")
//取代beans.config.xml及hibernate.cfg.xml
public class RootAppConfig_beans {
	@Autowired
	private Environment env;

	//取代context.xml的資料庫連線設定
	@Bean
	public DataSource sqlServerDataSource() {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		
		String driverName = env.getRequiredProperty("datasource.driverClassName");
		String url = env.getRequiredProperty("datasource.jdbcurl");
		String username = env.getRequiredProperty("datasource.username");
		String password = env.getRequiredProperty("datasource.password");
		
		System.out.println("driverName:" + driverName);
		System.out.println("url:" + url);
		System.out.println("username:" + username);
		System.out.println("password:" + password);
		
		ds.setUser(username);
		ds.setPassword(password);
		try {
			ds.setDriverClass(driverName);
		} catch (Exception e) { //PropertyVetoException 牽涉財產的異常
			e.printStackTrace();
		}
		ds.setJdbcUrl(url);
		ds.setInitialPoolSize(4); //連線池初始數量
		ds.setMaxPoolSize(8);
		return ds;
	}
	
	@Bean(name="sessionFactory")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
		factory.setDataSource(sqlServerDataSource());//取代sessionFactory的bean
		factory.setPackagesToScan(new String[] {
					"_01_model"
				});//取代hibernate.cfg.xml的mapping設定
		factory.setHibernateProperties(additionalPropertiesMsSQL());//見下
		return factory;
	}
	
	//取代tx:annotation-driven及其bean
	@Bean(name="transactionManager")
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
         HibernateTransactionManager txManager = new HibernateTransactionManager();
         txManager.setSessionFactory(sessionFactory);
         return txManager;
      }	
	
	//取代hibernate.cfg.xml的property設定
	private Properties additionalPropertiesMsSQL() {
		Properties properties=new Properties();
		properties.put("hibernate.dialect", org.hibernate.dialect.SQLServerDialect.class);
		properties.put("hibernate.show_sql", Boolean.TRUE);
		properties.put("hibernate.format_sql", Boolean.TRUE);
		properties.put("hibernate.current_session_context_class", "thread");
		properties.put("default_batch_fetch_size", 16); //批量抓取預設數量 (資料緩存)
		properties.put("hibernate.hbm2ddl.auto", "update"); //無資料表也可以自己建
		return properties;
	}
}
