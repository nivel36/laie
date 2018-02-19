package ged.ejb.core.action;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class ActionDaoJpa extends AbstractDaoJpa<Action> implements ActionDao {

	@Override
	public List<Action> findAllByUser(final User user) {
		Objects.requireNonNull(user);
		return this.findByQuery(Action.class, "Action.findAllByUser", map("user", user), 10, 0);
	}

	@Override
	public List<Action> findLastActions() {
		return this.findByQuery(Action.class, "Action.findLastActions", 25, 0);
	}

	@Override
	public Class<Action> getType() {
		return Action.class;
	}

	@Override
	public List<Action> search(final String searchText) {
		throw new UnsupportedOperationException();
	}
}