package ged.ejb.activity;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class ActivityTypeDao extends AbstractDao<ActivityType> {

	@Override
	protected Class<ActivityType> getType() {
		return ActivityType.class;
	}

	@Override
	public String[] searchFields() {
		return null;
	}
}
