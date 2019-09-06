package ged.ejb.event;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class EventTypeDao extends AbstractDao<EventType> {

	@Override
	protected Class<EventType> getType() {
		return EventType.class;
	}

	@Override
	public String[] searchFields() {
		return null;
	}
}
