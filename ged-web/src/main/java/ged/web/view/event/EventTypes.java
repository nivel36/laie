package ged.web.view.event;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.model.Page;
import ged.ejb.event.EventType;
import ged.ejb.event.EventTypeService;

@Named
@ApplicationScoped
public class EventTypes implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private transient EventTypeService eventTypeService;

	private List<EventType> listOfEventTypes;

	public List<EventType> getList() {
		return this.listOfEventTypes;
	}

	@PostConstruct
	public void init() {
		this.listOfEventTypes = this.eventTypeService.findAll(Page.ALL);
	}

	public void setEventTypeService(final EventTypeService eventTypeService) {
		this.eventTypeService = eventTypeService;
	}
}
