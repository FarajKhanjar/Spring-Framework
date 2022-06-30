package ajbc.learn.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import ajbc.learn.models.Category;

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
		category.setInActive(1);
		updateCategory(category);

	}
	
	@Override
	public List<Category> getAllCategories() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Category.class);
		return (List<Category>)template.findByCriteria(criteria);
	}
	
	@Override
	public List<Category> getActiveCategories(int inActive) throws DaoException {	
		List<Category> allCategories = getAllCategories();
		List<Category> categoriesInActive = new ArrayList<Category>();
		for(Category c : allCategories)
			if(c.getInActive()==inActive)
				categoriesInActive.add(template.get(Category.class, c.getCategoryId()));

		if (categoriesInActive.isEmpty())
			throw new DaoException("InActive check can be 0=active or 1=inActive");
		return categoriesInActive;
	}
	
	

	@Override
	public long count() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Category.class);
		criteria.setProjection(Projections.rowCount());
		return (long)template.findByCriteria(criteria).get(0);
	}

	
}