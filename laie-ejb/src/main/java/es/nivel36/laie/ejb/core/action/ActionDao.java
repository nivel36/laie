package es.nivel36.laie.ejb.core.action;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.util.Parameters;
import es.nivel36.laie.ejb.user.User;

public class ActionDao extends AbstractDao {

	public void insertAction(final Action action) {
		Objects.requireNonNull(action);
		super.insert(action);
	}

	public List<Action> findAllByUser(final User user, final Page page) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(page);
		final String namedQuery = "Action.findByUser";
		final Parameters parameters = map("user", user);
		return this.findByQuery(Action.class, namedQuery, parameters, page);
	}

	public long countAllByUser(final User user) {
		Objects.requireNonNull(user);
		final String namedQuery = "Action.countByUser";
		final Parameters parameters = map("user", user);
		final Long count = this.findByQuery(Long.class, namedQuery, parameters);
		return count.longValue();
	}
	
	public List<Action> findAll(final Page page) {
		Objects.requireNonNull(page);
		final String namedQuery = "Action.findAll";
		return this.findByQuery(Action.class, namedQuery, null, page);
	}

	public long countAll() {
		final String namedQuery = "Action.countAll";
		final Long count = this.findByQuery(Long.class, namedQuery, null);
		return count.longValue();
	}
}
