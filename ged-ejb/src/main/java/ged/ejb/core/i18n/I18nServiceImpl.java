package ged.ejb.core.i18n;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@Stateless
public class I18nServiceImpl implements I18nService {

	@Inject
	private EntityManager entityManager;

	@Override
	public List<I18nString> findAll() {
		final CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<I18nString> criteriaQuery = cb.createQuery(I18nString.class);
		return this.entityManager.createQuery(criteriaQuery).getResultList();
	}
}
