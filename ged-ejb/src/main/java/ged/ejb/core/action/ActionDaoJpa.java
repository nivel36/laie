package ged.ejb.core.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class ActionDaoJpa extends AbstractDao<Action> implements ActionDao {

	private static final Logger logger = LoggerFactory.getLogger(ActionDaoJpa.class.getName());

	@Inject
	public ActionDaoJpa(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<Action> findAllByUser(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Find all actions of the user {}", user.getFullName());
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", user);
		return findByTypedQuery(Action.class, "Action.findAllByUser", parameters, 10, 0);
	}

	@Override
	public Class<Action> getType() {
		return Action.class;
	}
}
