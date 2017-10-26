package ged.ejb.core.action;

import java.util.List;

import ged.ejb.core.model.Dao;
import ged.ejb.user.User;

public interface ActionDao extends Dao< Action> {

	List<Action> findAllByUser(final User user);
	
	List<Action> findLastActions();
}