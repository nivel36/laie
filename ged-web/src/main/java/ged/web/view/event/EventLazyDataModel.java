package ged.web.view.event;

import java.util.Objects;

import ged.ejb.core.AbstractService;
import ged.ejb.event.Event;
import ged.ejb.event.EventService;
import ged.web.core.view.AbstractLazyDataModel;

public class EventLazyDataModel extends AbstractLazyDataModel<Event> {

	private static final long serialVersionUID = 1L;

	private transient EventService eventService;

	public EventLazyDataModel(final EventService eventService) {
		Objects.requireNonNull(eventService, "EventService can't be null");
		this.eventService = eventService;
	}

	@Override
	protected AbstractService<Event> getService() {
		return this.eventService;
	}
}
