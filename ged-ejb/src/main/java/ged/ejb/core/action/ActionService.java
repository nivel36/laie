package ged.ejb.core.action;

import java.util.List;

import ged.ejb.core.CrudService;
import ged.ejb.user.User;

public interface ActionService extends CrudService<Long, Action> {

	List<Action> findAllByUser(final User user);
}