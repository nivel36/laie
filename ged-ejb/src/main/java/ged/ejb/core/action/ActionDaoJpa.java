package ged.ejb.core.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class ActionDaoJpa extends AbstractDao<Long, Action> implements ActionDao {

	private final Logger logger;

	@Inject
	public ActionDaoJpa(final Logger logger, @Repository final PersistenceFacade persistenceFacade) {
		super(persistenceFacade);
		this.logger = logger;
	}

	@Override
	public List<Action> findAllByUser(final User user) {
		if (user == null) {
			throw new NullPointerException();
		}
		this.logger.log(Level.FINE, "Buscando todas las acciones del usuario {}", user.getFullName());
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", user);
		return this.persistenceFacade.findByTypedQuery(Action.class, "Action.findAllByUser", parameters, 10, 0);
	}

	@Override
	public Class<Action> getClazz() {
		return Action.class;
	}
}
