package ajbc.learn.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import ajbc.learn.models.Category;
import ajbc.learn.models.Supplier;

@SuppressWarnings("unchecked")
@Component(value="ht_Category_Dao")
public class HibernateTemplateCategoryDao implements CategoryDao {
	
	@Autowired
	private HibernateTemplate template;

	@Override
	public void addCategory(Category category) throws DaoException {
		// open session /connection to db
		template.persist(category);
		// close session
	}

	@Override
	public void updateCategory(Category category) throws DaoException {
		template.merge(category);
	}

	@Override
	public Category getCategory(Integer categoryId) throws DaoException {
		Category category = template.get(Category.class, categoryId);
		if (category ==null)
			throw new DaoException("No Such Category in DB");
		return category;
	}

	@Override
	public void deleteCategory(Integer categoryId) throws DaoException {
		Category category = getCategory(categoryId);
		category.setDescription("----DELETED----");
		updateCategory(category);

	}
	
	@Override
	public List<Category> getAllCategories() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Category.class);
		return (List<Category>)template.findByCriteria(criteria);
	}


	@Override
	public long count() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Category.class);
		criteria.setProjection(Projections.rowCount());
		return (long)template.findByCriteria(criteria).get(0);
	}

	
}