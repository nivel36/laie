package ged.ejb.activity;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class ActivityDao extends AbstractDao<Activity> {

	@Override
	protected Class<Activity> getType() {
		return Activity.class;
	}

	@Override
	public String[] searchFields() {
		return null;
	}
}
