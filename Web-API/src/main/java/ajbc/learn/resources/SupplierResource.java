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
import ajbc.learn.dao.SupplierDao;
import ajbc.learn.models.ErrorMessage;
import ajbc.learn.models.Product;
import ajbc.learn.models.Supplier;

@RequestMapping("/suppliers")
@RestController
public class SupplierResource {

	@Autowired
	SupplierDao dao;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Supplier>> getAllSuppliers() throws DaoException{
		List<Supplier> list = dao.getAllSuppliers();
		if(list==null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(list);
	}
	
//	@RequestMapping(method = RequestMethod.GET, path="/{id}")
//	public ResponseEntity<?> getSuppliersById(@PathVariable Integer id) {
//		
//		Supplier supplier;
//		try {
//			supplier = dao.getSupplier(id);
//			return ResponseEntity.ok(supplier);
//			
//		} catch (DaoException e) {
//			ErrorMessage errorMessage = new ErrorMessage();
//			errorMessage.setData(e.getMessage());
//			errorMessage.setMessage("Failed to get supplier with id: "+id);
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
//		}
//	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addSupplier(@RequestBody Supplier supplier) {
		
		try {
			dao.addSupplier(supplier);
			supplier = dao.getSupplier(supplier.getSupplierId());
			return ResponseEntity.status(HttpStatus.CREATED).body(supplier);
			
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("Failed to add supplier to DB");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMessage);
		}

	}
	

	@RequestMapping(method = RequestMethod.PUT, path="/{id}")
	public ResponseEntity<?> updateSupplier(@RequestBody Supplier supplier, @PathVariable Integer id) {
		
		try {
			supplier.setSupplierId(id);
			dao.updateSupplier(supplier);
			supplier = dao.getSupplier(supplier.getSupplierId());
			return ResponseEntity.status(HttpStatus.OK).body(supplier);
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("failed to update supplier in db");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMessage);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path="/{id}")
	public ResponseEntity<?> deleteSupplier(@PathVariable Integer id) {
		
		try {
			Supplier supplier = dao.getSupplier(id);
			dao.deleteSupplier(id);
			supplier = dao.getSupplier(id);
			return ResponseEntity.status(HttpStatus.OK).body(supplier);
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("failed to delete supplier from db");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMessage);
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET, path="/byCity/{city}")
	public ResponseEntity<?> getSuppliersInCity(@PathVariable String city) {
	
		List<Supplier> suppliersInCity;
		try {
			suppliersInCity = dao.getSupplierByCity(city);
			return ResponseEntity.ok(suppliersInCity);
			
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("Failed to get supplier in: "+city);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}

	}

}
