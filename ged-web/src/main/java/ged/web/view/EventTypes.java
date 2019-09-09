package ged.web.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.event.EventService;
import ged.ejb.event.EventType;

@Named
@ApplicationScoped
public class EventTypes implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private transient EventService eventService;

	private List<EventType> listOfEventTypes;

	public List<EventType> getList() {
		return this.listOfEventTypes;
	}

	@PostConstruct
	public void init() {
		this.listOfEventTypes = this.eventService.findAllEventTypes();
	}

	public void setEventService(final EventService eventService) {
		this.eventService = eventService;
	}
}
