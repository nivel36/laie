package es.nivel36.laie.ejb.core.action;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDao;

@Stateless
public class ActionService {
	
	@Inject
	@Repository
	private UserDao userDao;

	public void addAction(final Action action) {
		Objects.requireNonNull(action);
		final User user = action.getUser();
		userDao.update(user);
	}


	public void setUserDao(final UserDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}

}
