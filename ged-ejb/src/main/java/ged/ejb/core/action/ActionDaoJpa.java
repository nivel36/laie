package ged.ejb.core.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class ActionDaoJpa extends AbstractDao<Long, Action> implements ActionDao {

	private static final Logger logger = Logger.getLogger(ActionDaoJpa.class.getName());

	@Inject
	public ActionDaoJpa(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<Action> findAllByUser(final User user) {
		Objects.requireNonNull(user);
		logger.log(Level.FINE, "Find all actions of the user {}", user.getFullName());
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", user);
		return findByTypedQuery(Action.class, "Action.findAllByUser", parameters, 10, 0);
	}

	@Override
	public Class<Action> getType() {
		return Action.class;
	}
}
