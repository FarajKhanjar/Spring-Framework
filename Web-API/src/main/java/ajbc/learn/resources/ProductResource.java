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

import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.Category;
import ajbc.learn.models.ErrorMessage;
import ajbc.learn.models.Product;
import ajbc.learn.models.Supplier;

@RequestMapping("/products")
@RestController
public class ProductResource {

	@Autowired
	ProductDao dao;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getAllProducts() throws DaoException{
		List<Product> list = dao.getAllProducts();
		if(list==null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(list);
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/priceRange")
	public ResponseEntity<List<Product>> getProductsByPriceRange(@RequestParam(name="min") Optional<Double> min,@RequestParam(name="max") Optional<Double> max) throws DaoException{
		List<Product> list;
		if(min.isPresent() && max.isPresent())
			list = dao.getProductsByPriceRange(min.get(), max.get());
		else
			list = dao.getAllProducts();
		
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
	public ResponseEntity<?> getProductsById(@PathVariable Integer id) {
		
		Product product;
		try {
			product = dao.getProduct(id);
			return ResponseEntity.ok(product);
			
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("Failed to get product with id: "+id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		
		try {
			dao.addProduct(product);
			product = dao.getProduct(product.getProductId());
			return ResponseEntity.status(HttpStatus.CREATED).body(product);
			
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("Failed to add product to DB");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMessage);
		}

	}
	
	@RequestMapping(method = RequestMethod.PUT, path="/{id}")
	public ResponseEntity<?> updateProduct(@RequestBody Product prod, @PathVariable Integer id) {
		
		try {
			prod.setProductId(id);
			dao.updateProduct(prod);
			prod = dao.getProduct(prod.getProductId());
			return ResponseEntity.status(HttpStatus.OK).body(prod);
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("failed to update product in db");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMessage);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path="/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
		
		try {
			Product prod = dao.getProduct(id);
			dao.deleteProduct(id);
			prod = dao.getProduct(id);
			return ResponseEntity.status(HttpStatus.OK).body(prod);
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("failed to delete product from db");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMessage);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/{id}/category")
	public ResponseEntity<?> getCategoryByProductId(@PathVariable Integer id) {
		
		try {
			Category category = dao.getCategoryByProductId(id);
			return ResponseEntity.status(HttpStatus.OK).body(category);
			
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("failed, make sure that you write 'category'");
			return ResponseEntity.status(HttpStatus.valueOf(404)).body(errorMessage);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/{id}/supplier")
	public ResponseEntity<?> getSupplierByProductId(@PathVariable Integer id) {
		
		try {
			Supplier supplier = dao.getSupplierByProductId(id);
			return ResponseEntity.status(HttpStatus.OK).body(supplier);
			
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("failed, make sure that you write 'supplier'");
			return ResponseEntity.status(HttpStatus.valueOf(404)).body(errorMessage);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/inCategory/{categoryId}")
	public ResponseEntity<?> getProductsInCategory(@PathVariable Integer categoryId) {
	
		List<Product> productsInCategory;
		try {
			productsInCategory = dao.getProductsInCategory(categoryId);
			return ResponseEntity.ok(productsInCategory);
			
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("Failed to get products in category: "+categoryId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}

	}
	
	@RequestMapping(method = RequestMethod.GET, path="/ofSupplier/{supplierId}")
	public ResponseEntity<?> getProductsOfSupplier(@PathVariable Integer supplierId) {
	
		List<Product> productsOfSupplier;
		try {
			productsOfSupplier = dao.getProductsOfSupplier(supplierId);
			return ResponseEntity.ok(productsOfSupplier);
			
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("Failed to get products of supplier: "+supplierId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}

	}
	
}