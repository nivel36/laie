package ged.ejb.core.action;

import static ged.ejb.core.model.QueryParameter.with;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class ActionDaoJpa extends AbstractDaoJpa<Action> implements ActionDao {

	@Inject
	public ActionDaoJpa(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<Action> findAllByUser(final User user) {
		Objects.requireNonNull(user);
		return findByTypedQuery(Action.class, "Action.findAllByUser", with("user", user).parameters(), 10, 0);
	}

	@Override
	public Class<Action> getType() {
		return Action.class;
	}

	@Override
	public List<Action> findLastActions() {
		return findByTypedQuery(Action.class, "Action.findLastActions", 25, 0);
	}
}