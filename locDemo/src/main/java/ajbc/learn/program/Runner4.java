package ajbc.learn.program;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



import ajbc.learn.config.AppConfig;
import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.HibernateTemplateProductDao;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.Product;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Runner4 
{
	public static void main(String[] args) throws DaoException 
	{
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);) {
			
			ProductDao dao = ctx.getBean("htDao", ProductDao.class);
			//System.out.println(dao.getClass().getName()); //first check
			
			List<Product> products = dao.getAllProducts();
			System.out.println(products.size());
			
			System.out.println("----------------------------------");
			Double min = 50.0;
			Double max = 200.0;
			products = dao.getProductsByPriceRange(min, max);
			System.out.println(products.size()); 
			products.forEach(System.out::println);
			
			//products = dao.getProductsByPriceRange(max, min); // it doesn't work, must min to-> max.
			//System.out.println(products.size()); 
			//products.forEach(System.out::println);
			
			System.out.println("----------------------------------");
			Product product1 = dao.getProduct(1);
			System.out.println(product1);
			Product product2 = dao.getProduct(0); //null
			System.out.println(product2);
			
			
			System.out.println("----------------------------------");
//			product1.setUnitPrice(product1.getUnitPrice()+1);
//			try {
//			dao.updateProduct(product1);
//			} catch(DaoException e) {
//				System.out.println(e);
//			}
			System.out.println("Continute till end of main...");
			
			
		}
			
		
	}
}
