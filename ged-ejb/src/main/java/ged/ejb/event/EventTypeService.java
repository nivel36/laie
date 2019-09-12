package ged.ejb.event;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class EventTypeService extends AbstractService<EventType>{
	
	@Inject
	@Repository
	private EventTypeDao eventTypeDao;

	@Override
	protected AbstractDao<EventType> getDao() {
		return eventTypeDao;
	}

}
