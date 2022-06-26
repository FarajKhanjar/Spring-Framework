package ajbc.learn.program;

import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.JdbcProductDao;
import ajbc.learn.dao.MongoProductDao;

import ajbc.learn.config.AppConfig;
import ajbc.learn.dao.ProductDao;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Runner {

	public static void main(String[] args) throws DaoException {
		
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);
		
		// The dependency
		//ProductDao dao;
		
		//The old way:
				/*
				dao = new MongoProductDao();
				System.out.println("The number of products in the DB: "+dao.count());
				dao = new JdbcProductDao();
				System.out.println("The number of products in the DB: "+dao.count());
				*/
		
		
		
		//The new way:
		//The spring container
		//ProductDao dao2;
		ProductDao dao1;
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		//dao1=ctx.getBean("jdbcDao",ProductDao.class);
		//dao2=ctx.getBean("mongoDao",ProductDao.class);
		//System.out.println("dao is an instanceof "+dao1.getClass().getName());
		//System.out.println("dao1 == dao2 ? "+(dao1==dao2));
		
		dao1=ctx.getBean("jdbcDao",ProductDao.class);
		System.out.println("---------------------------------------");
		System.out.println("The number of product in the DB is "+dao1.count());
		System.out.println("---------------------------------------");
		
		System.out.println("-----------------Again-----------------");
		System.out.println("The number of product in the DB is "+dao1.count());
		System.out.println("---------------------------------------");
		
		System.out.println("-----------------Again-----------------");
		System.out.println("The number of product in the DB is "+dao1.count());
		System.out.println("---------------------------------------");
		
		System.out.println("-----------------Again-----------------");
		System.out.println("The number of product in the DB is "+dao1.count());
		System.out.println("---------------------------------------");
		
	}

}
