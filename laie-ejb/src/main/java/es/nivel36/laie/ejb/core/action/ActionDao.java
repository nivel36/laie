package es.nivel36.laie.ejb.core.action;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.user.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class ActionDao extends AbstractDao {

	public List<Action> findAllByUser(final User user, final Page page) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT a
				FROM Action a
				LEFT JOIN FETCH a.user
				WHERE a.user = :user
				ORDER BY a.date DESC
				""";
		final TypedQuery<Action> query = this.em.createQuery(jpql, Action.class);
		query.setParameter("user", user);
		this.paginate(page, query);
		return query.getResultList();
	}

	public long countAllByUser(final User user) {
		Objects.requireNonNull(user);
		final String jpql = """
				SELECT count(a)
				FROM Action a
				WHERE a.user = :user
				""";
		final TypedQuery<Long> query = this.em.createQuery(jpql, Long.class);
		query.setParameter("user", user);
		return query.getSingleResult();
	}

	public List<Action> findAll(final Page page) {
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT a
				FROM Action a
				ORDER BY a.date DESC
				""";
		final TypedQuery<Action> query = this.em.createQuery(jpql, Action.class);
		this.paginate(page, query);
		return query.getResultList();
	}

	public long countAll() {
		final String jpql = """
				SELECT count(a)
				FROM Action a
				""";
		final TypedQuery<Long> query = this.em.createQuery(jpql, Long.class);
		return query.getSingleResult();
	}
}
