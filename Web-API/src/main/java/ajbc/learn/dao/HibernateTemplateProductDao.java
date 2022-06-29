package ajbc.learn.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import ajbc.learn.models.Product;

@Component(value="htDao")
public class HibernateTemplateProductDao implements ProductDao {
	
	@Autowired
	private HibernateTemplate template;

	@Override
	public void addProduct(Product product) throws DaoException {
		// open session/connection to DB
		template.persist(product);
		// close session

	}

	@Override
	public void updateProduct(Product product) throws DaoException {
		template.merge(product);
	}

	@Override
	public Product getProduct(Integer productId) throws DaoException {
		return template.get(Product.class, productId);
	}

	@Override
	public void deleteProduct(Integer productId) throws DaoException {
		Product prod = getProduct(productId);
		prod.setDiscontinued(1);
		updateProduct(prod);     //delete => update. We don't use delete to any thing in DB
	}


	@Override
	public List<Product> getAllProducts() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		return (List<Product>)template.findByCriteria(criteria);
	}
	
	@Override
	public List<Product> getProductsByPriceRange(Double min, Double max) throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		Criterion criterion = Restrictions.between("unitPrice", min, max); // "unitPrice" name in java/Product class.
		criteria.add(criterion);
		return (List<Product>)template.findByCriteria(criteria);
	}

	@Override
	public List<Product> getProductsInCategory(Integer categoryId) throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		Criterion criterion = Restrictions.eq("categoryId", categoryId);
		return (List<Product>)template.findByCriteria(criteria);
	}

	@Override
	public List<Product> getProductsNotInStock() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		Criterion criterion = Restrictions.eq("unitsInStock", 0);
		return (List<Product>)template.findByCriteria(criteria);
	}

	@Override
	public List<Product> getProductsOnOrder() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		Criterion criterion = Restrictions.gt("unitsOnOrder", 0);
		return (List<Product>)template.findByCriteria(criteria);
	}

	@Override
	public List<Product> getDiscontinuedProducts() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		Criterion criterion = Restrictions.eq("discontinued", 1);
		return (List<Product>)template.findByCriteria(criteria);
	}

	@Override
	public long count() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		criteria.setProjection(Projections.rowCount()); 
		//alias=change names. //distinct=elements that doesn't return on each others in list.
		return (long)template.findByCriteria(criteria).get(0);
	}

}
