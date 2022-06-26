package ajbc.learn.program;

import javax.persistence.GenerationType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;


import ajbc.learn.config.AppConfig;
import ajbc.learn.dao.DaoException;
import ajbc.learn.models.Category;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Runner3 
{
	public static void main(String[] args) throws DaoException 
	{
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
			SessionFactory factory = ctx.getBean(SessionFactory.class);
			Session session = factory.openSession();) {
			
			//read from DB
			Category cat1 = session.get(Category.class, 1); //"select * from Category where CategoryID = 1"
			System.out.println(cat1);
			System.out.println("---------");
			
			//write to DB
			//the problem with write method is Race conditions that maybe case while reading another object from DB,
			//the solution is to deal with transactions.
			
			Category cat2 = new Category();
			//cat2.setCategoryId(9); because id in Categoey.class is (strategy=GenerationType.IDENTITY), = Sql put Auto-Id.
			cat2.setCatName("Baby");
			cat2.setDescription("Diapers, Napkins, Pacifiers, Bottles");
			
			Transaction transaction = session.beginTransaction();
			try {
				
				session.persist(cat2); //"insert into Category values (?,?)"
				transaction.commit();
				System.out.println("Category in DB");
				
			} catch(Exception e) {
				
			transaction.rollback();
			System.out.println("Inserataion rolled back\n"+e);
			
			}
			
			
		}

		
	}
}
