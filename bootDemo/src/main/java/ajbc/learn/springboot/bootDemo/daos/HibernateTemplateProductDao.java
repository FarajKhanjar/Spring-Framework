package ajbc.learn.springboot.bootDemo.daos;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import ajbc.learn.springboot.bootDemo.entities.Category;
import ajbc.learn.springboot.bootDemo.entities.Product;
import ajbc.learn.springboot.bootDemo.entities.Supplier;


@SuppressWarnings("unchecked")
@Component(value="ht_Product_Dao")
public class HibernateTemplateProductDao implements ProductDao {
	
	@Autowired
	private HibernateTemplate template;

	@Override
	public void addProduct(Product product) throws DaoException {
		// open session /connection to db
		template.persist(product);
		// close session
	}

	@Override
	public void updateProduct(Product product) throws DaoException {
		template.merge(product);
	}

	@Override
	public Product getProduct(Integer productId) throws DaoException {
		Product prod = template.get(Product.class, productId);
		if (prod ==null)
			throw new DaoException("No Such Product in DB");
		return prod;
	}

	@Override
	public void deleteProduct(Integer productId) throws DaoException {
		Product prod = getProduct(productId);
		prod.setDiscontinued(1);
		updateProduct(prod);
	}
	
	@Override
	public List<Product> getAllProducts() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		return (List<Product>)template.findByCriteria(criteria);
	}

	@Override
	public List<Product> getProductsByPriceRange(Double min, Double max) throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		Criterion criterion = Restrictions.between("unitPrice", min, max);
		criteria.add(criterion);
		return (List<Product>)template.findByCriteria(criteria);
	}

	@Override
	public List<Product> getProductsNotInStock() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		criteria.add(Restrictions.eq("unitsInStock", 0));
		return (List<Product>)template.findByCriteria(criteria);
	}

	@Override
	public List<Product> getProductsOnOrder() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		criteria.add(Restrictions.gt("unitsOnOrder", 0));
		return (List<Product>)template.findByCriteria(criteria);
	}

	@Override
	public List<Product> getDiscontinuedProducts() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		criteria.add(Restrictions.eq("discontinued", 1));
		return (List<Product>)template.findByCriteria(criteria);
	}

	@Override
	public long count() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		criteria.setProjection(Projections.rowCount());
		return (long)template.findByCriteria(criteria).get(0);
	}
	
	@Override
	public Category getCategoryByProductId(Integer productId) throws DaoException {	
		Product prod = template.get(Product.class, productId);
		int categoryId = prod.getCategoryId();
		Category category = template.get(Category.class, categoryId);
		if (category ==null)
			throw new DaoException("No Such Product in DB with id: "+productId);
		return category;
	}
	
	@Override
	public Supplier getSupplierByProductId(Integer productId) throws DaoException {	
		Product prod = template.get(Product.class, productId);
		int supplierId = prod.getCategoryId();
		Supplier supplier = template.get(Supplier.class, supplierId);
		if (supplier ==null)
			throw new DaoException("No Such Product in DB with id: "+productId);
		return supplier;
	}
	
	@Override
	public List<Product> getProductsInCategory(Integer categoryId) throws DaoException {	
		List<Product> allProducts = getAllProducts();
		List<Product> productsInCategory = new ArrayList<Product>();
		for(Product p : allProducts)
			if(p.getCategoryId()==categoryId)
				productsInCategory.add(template.get(Product.class, p.getProductId()));

		if (productsInCategory.isEmpty())
			throw new DaoException("No Such Category: "+categoryId+" in DB");
		return productsInCategory;
	}
	
	@Override
	public List<Product> getProductsOfSupplier(Integer supplierId) throws DaoException {	
		List<Product> allProducts = getAllProducts();
		List<Product> productsOfSupplier = new ArrayList<Product>();
		for(Product p : allProducts)
			if(p.getSupplierId()==supplierId)
				productsOfSupplier.add(template.get(Product.class, p.getProductId()));

		if (productsOfSupplier.isEmpty())
			throw new DaoException("No Such Supplier: "+supplierId+" in DB");
		return productsOfSupplier;
	}
	
}