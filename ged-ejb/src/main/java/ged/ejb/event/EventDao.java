package ged.ejb.event;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class EventDao extends AbstractDao<Event> {

	@Override
	protected Class<Event> getType() {
		return Event.class;
	}

	@Override
	public String[] searchFields() {
		return null;
	}
}
