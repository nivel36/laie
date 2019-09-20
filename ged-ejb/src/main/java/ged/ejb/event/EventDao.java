package ged.ejb.event;

import java.util.List;
import java.util.Objects;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;

@Repository
public class EventDao extends AbstractDao<Event> {

	@Override
	protected Class<Event> getType() {
		return Event.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] {};
	}
	
	public List<Event> findLastEvents (final Page page) {
		Objects.requireNonNull(page, "Page can't be null");
		return this.getPersistenceFacade().findByQuery(Event.class, "Event.findLast", null, page);		
	}
}
