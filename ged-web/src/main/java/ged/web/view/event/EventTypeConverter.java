package ged.web.view.event;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.event.EventType;
import ged.ejb.event.EventTypeService;
import ged.web.core.view.AbstractConverter;

@FacesConverter(managed = true, forClass = EventType.class)
public class EventTypeConverter extends AbstractConverter<EventType>{
	
	@Inject
	private EventTypeService eventTypeService;

	@Override
	protected AbstractService<EventType> getService() {
		return eventTypeService;
	}

	public void setEventTypeService(EventTypeService eventTypeService) {
		this.eventTypeService = eventTypeService;
	}
}
