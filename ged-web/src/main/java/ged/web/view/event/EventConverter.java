package ged.web.view.event;

import javax.faces.convert.FacesConverter;

import ged.ejb.core.AbstractService;
import ged.ejb.event.Event;
import ged.ejb.event.EventService;
import ged.web.core.view.AbstractConverter;

@FacesConverter(managed = true, forClass = Event.class)
public class EventConverter extends AbstractConverter<Event> {

	private EventService eventService;

	@Override
	protected AbstractService<Event> getService() {
		return this.eventService;
	}

	public void setEventService(final EventService eventService) {
		this.eventService = eventService;
	}
}