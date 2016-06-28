package ged.ejb.core.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class ActionDaoJpa extends AbstractDao<Long, Action> implements ActionDao {

	@Override
	public List<Action> findAllByUser(final User user) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", user);
		return this.persistenceFacade.findByTypedQuery(Action.class, "Action.findAllByUser", parameters, 10, 0);
	}

	@Override
	public Class<Action> getClazz() {
		return Action.class;
	}
}
