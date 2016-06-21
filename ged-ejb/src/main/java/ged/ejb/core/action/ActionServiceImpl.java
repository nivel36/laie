package ged.ejb.core.action;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.Repository;
import ged.ejb.user.User;

@Stateless
public class ActionServiceImpl extends AbstractService<Long, Action> implements ActionService {

	@Inject
	@Repository
	private ActionDao dao;

	@Override
	public List<Action> findAllByUser(final User user) {
		return this.dao.findAllByUser(user);
	}

	@Override
	protected ActionDao getDao() {
		return this.dao;
	}

	public void setDao(final ActionDao dao) {
		this.dao = dao;
	}
}
