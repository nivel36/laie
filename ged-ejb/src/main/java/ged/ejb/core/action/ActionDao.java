package ged.ejb.core.action;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class ActionDao extends AbstractDao<Action> {

	public List<Action> findAllByUser(final User user) {
		Objects.requireNonNull(user);
		return this.findByQuery(Action.class, "Action.findAllByUser", map("user", user), Page.ALL);
	}

	public List<Action> findLastActions() {
		return this.findByQuery(Action.class, "Action.findLastActions", null, Page.ALL);
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