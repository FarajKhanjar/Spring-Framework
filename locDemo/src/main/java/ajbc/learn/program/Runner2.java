package ajbc.learn.program;

import ajbc.learn.dao.JdbcProductDao;
import ajbc.learn.dao.MongoProductDao;

import ajbc.learn.config.AppConfig;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.Category;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Runner2 {

	public static void main(String[] args) {
		
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);
		
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);
		
		//connect between row and class.
		RowMapper<Category> rowMapper = new RowMapper<Category>( ) {

			@Override
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
				Category cat = new Category();
				cat.setCategoryId(rs.getInt("categoryId"));
				cat.setCategoryName(rs.getString("categoryName"));
				cat.setDescription(rs.getString("description"));
				cat.setPicture(rs.getBytes("picture"));
				return cat;
			}
			
		};
		
		
		
		//------------------------[CRUD]---------------------------//
		//insertNewShipper(template);
		//updateShipperPhone(template,4,"(054) 245-9909");
		//updateShipperCompany(template,5,"Good Travel");
		
		//-----------[CRUD that print a single value]-------------//
		//printNumProducts(template);
		//printShipperName(template,5);
		//printCityOfCustomerById(template,"FISSA");
		
		//---------[CRUD that print a single row (using Map)]-----------//
		//printProductsDetails(template,3);
		
		//---------[CRUD that print a multi rows (using Map+List)]-----------//
		//printAnyOrderOfEmployeeId(template,50);
		//printAnyOrderOfEmployeeId(template,5);
		//printAllShippers(template);
		//printAllShippersNames(template);
		
		//---------[CRUD that print a .. using rowMapper]-----------//
		//printCategoryById(template,rowMapper, 5);
		//printAllCategories(template,rowMapper);
	
	}


	private static void insertNewShipper(JdbcTemplate template) {
		String sql = "insert into Shippers values(?,?)";
		int rowAffected = template.update(sql, "Faraj IL","123-456-789");
		System.out.println("Rows affected number: "+rowAffected);

	}
	
	private static void updateShipperPhone(JdbcTemplate template, int id, String phone) {
		String sql = "update Shippers set Phone=? where ShipperID = ?";
		int rowAffected = template.update(sql, phone ,id);
		System.out.println("Rows affected number: "+rowAffected);
		
	}
	
	private static void updateShipperCompany(JdbcTemplate template, int id, String companyName) {
		String sql = "update Shippers set CompanyName=? where ShipperID = ?";
		int rowAffected = template.update(sql, companyName ,id);
		System.out.println("Rows affected number: "+rowAffected);
		
	}
	
	private static void printNumProducts(JdbcTemplate template) {
		String sql = "select count(*) from products";
		int count = template.queryForObject(sql,int.class);
		System.out.println("count: "+count);
		
	}
	
	private static void printShipperName(JdbcTemplate template, int id) {
		String sql = "select CompanyName from Shippers where ShipperID = ?";
		String name = template.queryForObject(sql,String.class,id);
		System.out.println("name: "+name);
		
	}
	
	private static void printCityOfCustomerById(JdbcTemplate template, String id) {
		String sql = "select City from Customers where CustomerID = ?";
		String city = template.queryForObject(sql,String.class,id);
		System.out.println("city: "+city);
		
	}
	
	private static void printProductsDetails(JdbcTemplate template, int id) {
		String sql = "select * from Products where ProductId = ?";
		Map<String,Object> data = template.queryForMap(sql,id);
		System.out.println(data.keySet());
		System.out.println(data.values());
		
	}
	
	private static void printAnyOrderOfEmployeeId(JdbcTemplate template, int employeeId) {
		String sql = "select * from Orders where EmployeeID = ?";
		List<Map<String,Object>> data = template.queryForList(sql,employeeId);
		if(data != null && data.size()>0)
			System.out.println(data.get(0));
		else
			System.out.println("There is no any order for this employee");
		
	}
	
	private static void printAllShippers(JdbcTemplate template) {
		String sql = "select * from Shippers";
		List<Map<String,Object>> data = template.queryForList(sql);
		for(Map<String,Object> map:data)
			System.out.println(map);
		
	}
	
	private static void printAllShippersNames(JdbcTemplate template) {
		String sql = "select CompanyName from Shippers";
		List<String> list = template.queryForList(sql,String.class);
		for(String name:list)
			System.out.println(name);
		
	}
	
	private static void printCategoryById(JdbcTemplate template, RowMapper<Category> rowMapper, int id) {
		String sql = "select * from Categories where CategoryID = ?";
		Category cat = template.queryForObject(sql, rowMapper, id);
		System.out.println(cat);
		
		
	}
	
	private static void printAllCategories(JdbcTemplate template, RowMapper<Category> rowMapper) {
		String sql = "select * from Categories";
		List<Category> catList = template.query(sql, rowMapper);
		for(Category c:catList)
			System.out.println(c);
		
	}



}
