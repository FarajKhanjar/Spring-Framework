package ajbc.learn.resources;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ajbc.learn.dao.CategoryDao;
import ajbc.learn.dao.DaoException;
import ajbc.learn.models.Category;
import ajbc.learn.models.ErrorMessage;
import ajbc.learn.models.Supplier;


@RequestMapping("/categories")
@RestController
public class CategoryResource {

	@Autowired
	CategoryDao dao;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getAllCategories() throws DaoException{
		List<Category> list = dao.getAllCategories();
		if(list==null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(list);
	}
	
	//Another way:
//	@RequestMapping(method = RequestMethod.GET)
//	public ResponseEntity<List<Product>> getProductsByPriceRange(@RequestParam Map<String,String> map) throws DaoException{
//		List<Product> list;
//		Set<String> keys = map.keySet();
//		
//		if(keys.contains("min") && keys.contains("max"))
//			list = dao.getProductsByPriceRange(Double.parseDouble(map.get("min")),Double.parseDouble(map.get("max")));
//		else
//			list = dao.getAllProducts();
//		
//		if(list==null)
//			return ResponseEntity.notFound().build();
//		
//		return ResponseEntity.ok(list);
//	}
	
	
	@RequestMapping(method = RequestMethod.GET, path="/{id}")
	public ResponseEntity<?> getCategoriesById(@PathVariable Integer id) {
		
		Category category;
		try {
			category = dao.getCategory(id);
			return ResponseEntity.ok(category);
			
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("Failed to get category with id: "+id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addCategory(@RequestBody Category category) {
		
		try {
			dao.addCategory(category);
			category = dao.getCategory(category.getCategoryId());
			return ResponseEntity.status(HttpStatus.CREATED).body(category);
			
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("Failed to add category to DB");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMessage);
		}

	}
	
	@RequestMapping(method = RequestMethod.PUT, path="/{id}")
	public ResponseEntity<?> updateCategory(@RequestBody Category prod, @PathVariable Integer id) {
		
		try {
			prod.setCategoryId(id);
			dao.updateCategory(prod);
			prod = dao.getCategory(prod.getCategoryId());
			return ResponseEntity.status(HttpStatus.OK).body(prod);
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("failed to update category in db");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMessage);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path="/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
		
		try {
			Category category = dao.getCategory(id);
			dao.deleteCategory(id);
			category = dao.getCategory(id);
			return ResponseEntity.status(HttpStatus.OK).body(category);
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("failed to delete category from db");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMessage);
		}
	}

}
