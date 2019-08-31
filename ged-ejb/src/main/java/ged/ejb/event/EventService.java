package ged.ejb.event;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class EventService extends AbstractService<Event> {

	@Inject
	@Repository
	private EventDao eventDao;

	@Override
	protected AbstractDao<Event> getDao() {
		return this.eventDao;
	}
}
