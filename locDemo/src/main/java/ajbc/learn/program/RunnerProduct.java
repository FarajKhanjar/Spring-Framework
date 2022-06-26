package ajbc.learn.program;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import ajbc.learn.config.AppConfig;
import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.JdbcProductDao;
import ajbc.learn.models.Product;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

@ComponentScan(basePackages = { "ajbc.learn.dao" })
public class RunnerProduct 
{

	public static void main(String[] args) throws DaoException 
	{

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		// The spring container
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		JdbcProductDao jdbcProductDao = new JdbcProductDao();

		jdbcProductDao.setTemplate(ctx.getBean(JdbcTemplate.class));

		Product product = new Product("Malabi", 1, 3, "2 units", 10.5, 20, 5, 7, 4);

//		jdbcProductDao.addProduct(product);

//		product.setProductId(78);
//		product.setUnitPrice(9.5);
//		product.setProductName("MalabiD");
//		jdbcProductDao.updateProduct(product);

//		Product product2 = jdbcProductDao.getProduct(3);
//		System.out.println(product2);

//		jdbcProductDao.deleteProduct(78);

//		List<Product> products = jdbcProductDao.getAllProducts();
//		products.forEach(System.out::println);

//		List<Product> productsByRange = jdbcProductDao.getProductsByPriceRange(5.0,10.0);
//		productsByRange.forEach(System.out::println);

//		List<Product> productsByCategoty = jdbcProductDao.getProductsInCategory(3);
//		productsByCategoty.forEach(System.out::println);

//		List<Product> productsNotInStock = jdbcProductDao.getProductsNotInStock();
//		productsNotInStock.forEach(System.out::println);

//		List<Product> productsOnOrder = jdbcProductDao.getProductsOnOrder();
//		productsOnOrder.forEach(System.out::println);
		
//		List<Product> productsDiscontinued= jdbcProductDao.getDiscontinuedProducts();
//		productsDiscontinued.forEach(System.out::println);
		
		long rows = jdbcProductDao.count();
		System.out.println("There is: " + rows + " products");
		
	}
}
