package ged.ejb.core.action;

import java.util.List;

import ged.ejb.core.model.CrudDao;
import ged.ejb.user.User;

public interface ActionDao extends CrudDao<Long, Action> {

	List<Action> findAllByUser(final User user);

}