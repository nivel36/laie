package ged.ejb.event;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;

@Stateless
public class EventService extends AbstractService<Event> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private EventDao eventDao;
	
	@Inject
	@Repository
	private EventTypeDao eventTypeDao;

	@Override
	protected AbstractDao<Event> getDao() {
		return eventDao;
	}
	
	public List<EventType> findAllEventTypes() {
		logger.debug("Find all event types");
		return eventTypeDao.findAll(Page.ALL);
	}
}
