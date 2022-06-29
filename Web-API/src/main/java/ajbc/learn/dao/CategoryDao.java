package ajbc.learn.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ajbc.learn.models.Category;
import ajbc.learn.models.Supplier;

@Transactional(rollbackFor = {DaoException.class}, readOnly = true)
public interface CategoryDao {

	// CRUD operations
	@Transactional(readOnly = false) //update in DB => write
	public default void addCategory(Category category) throws DaoException {
		throw new DaoException("Method not implemented");
	}
	
	@Transactional(readOnly = false) //update in DB => write
	public default void updateCategory(Category category) throws DaoException {
		throw new DaoException("Method not implemented");
	}
	
	@Transactional(readOnly = false) //update in DB => write
	public default void deleteCategory(Integer categoryId) throws DaoException {
		throw new DaoException("Method not implemented");
	}

	public default Category getCategory(Integer categoryId) throws DaoException {
		throw new DaoException("Method not implemented");
	}


	// QUERIES
	public default List<Category> getAllCategories() throws DaoException {
		throw new DaoException("Method not implemented");
	}



	public default long count() throws DaoException {
		throw new DaoException("Method not implemented");
	}

	

}
