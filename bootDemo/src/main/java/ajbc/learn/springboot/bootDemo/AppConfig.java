package ajbc.learn.springboot.bootDemo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import ajbc.learn.springboot.bootDemo.entities.Category;
import ajbc.learn.springboot.bootDemo.entities.Product;
import ajbc.learn.springboot.bootDemo.entities.Supplier;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;




@EnableTransactionManagement
@EnableAspectJAutoProxy
@Configuration
@EnableWebMvc
public class AppConfig {


	private static final int INIT_SIZE = 10, MAX_SIZE = 100, MAX_WAIT = 500, MAX_IDLE = 50, MIN_IDLE = 2;
	@Value("${user}")
	private String user;
	@Value("${password}")
	private String password;
	@Value("${server_address}")
	private String serverAddress;
	@Value("${port}")
	private String port;
	@Value("${db_name}")
	private String databaseName;
	@Value("${server_name}")
	private String serverName;
	@Value("${driver_class_name}")
	private String driverClassName;
	

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(createUrl());
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driverClassName);
		
		dataSource.setInitialSize(INIT_SIZE);
		dataSource.setMaxTotal(MAX_SIZE);
		dataSource.setMaxWaitMillis(MAX_WAIT);
		dataSource.setMaxIdle(MAX_IDLE);
		dataSource.setMinIdle(MIN_IDLE);
		
		return dataSource;
	}
	
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource  dataSource) {
		LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
		factory.setDataSource(dataSource);
		factory.setAnnotatedClasses(Category.class, Supplier.class, Product.class);
		
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
		props.setProperty("hibernate.show_sql", "false");
		props.setProperty("hibernate.format_sql", "true");
		
		factory.setHibernateProperties(props);
		return factory;
	}
	
	
	
	@Bean
	public HibernateTemplate hibernateTemplate(SessionFactory sessionFactory) {
		return new HibernateTemplate(sessionFactory);
	}
	
	
	@Bean
	public HibernateTransactionManager txMgr(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}
	
	
	private String createUrl() {
		return "jdbc:sqlserver://" + serverAddress + ":" + port + ";databaseName=" + databaseName + ";servername="
				+ serverName + ";" + ";encrypt=false";
	}
}