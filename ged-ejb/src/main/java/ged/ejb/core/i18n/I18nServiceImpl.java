package ged.ejb.core.i18n;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class I18nServiceImpl implements I18nService {

	private final EntityManager entityManager;

	@Inject
	public I18nServiceImpl(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<I18nString> findAll() {
		final CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<I18nString> cq = cb.createQuery(I18nString.class);
		final Root<I18nString> root = cq.from(I18nString.class);
		final CriteriaQuery<I18nString> all = cq.select(root);
		final TypedQuery<I18nString> query = this.entityManager.createQuery(all);
		return query.getResultList();
	}
}
