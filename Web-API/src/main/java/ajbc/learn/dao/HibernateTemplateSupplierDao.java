package ajbc.learn.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import ajbc.learn.models.Product;
import ajbc.learn.models.Supplier;


@SuppressWarnings("unchecked")
@Component(value="ht_Supplier_Dao")
public class HibernateTemplateSupplierDao implements SupplierDao {
	
	@Autowired
	private HibernateTemplate template;

	@Override
	public void addSupplier(Supplier supplier) throws DaoException {
		// open session /connection to db
		template.persist(supplier);
		// close session
	}

	@Override
	public void updateSupplier(Supplier supplier) throws DaoException {
		template.merge(supplier);
	}

	@Override
	public Supplier getSupplier(Integer supplierId) throws DaoException {
		Supplier supplier = template.get(Supplier.class, supplierId);
		if (supplier ==null)
			throw new DaoException("No Such Supplier in DB");
		return supplier;
	}

	@Override
	public void deleteSupplier(Integer supplierId) throws DaoException {
		Supplier supplier = getSupplier(supplierId);
		supplier.setCompanyName("----DELETED----"+"["+supplier.getCompanyName()+"]");
		updateSupplier(supplier);

	}
	
	@Override
	public List<Supplier> getAllSuppliers() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Supplier.class);
		return (List<Supplier>)template.findByCriteria(criteria);
	}

	@Override
	public List<Supplier> getSuppliersInCity(String cityName) throws DaoException {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Supplier.class);
		criteria.add(Restrictions.eq("city", cityName));
		return (List<Supplier>)template.findByCriteria(criteria);
	}

	@Override
	public long count() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Supplier.class);
		criteria.setProjection(Projections.rowCount());
		return (long)template.findByCriteria(criteria).get(0);
	}

	
}