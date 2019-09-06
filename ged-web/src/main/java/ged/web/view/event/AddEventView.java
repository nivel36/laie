package ged.web.view.event;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ged.ejb.event.Event;
import ged.ejb.event.EventService;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class AddEventView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private Event event;

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	private transient EventService eventService;

	public String save() {
		this.eventService.save(event);
		return PageEnum.EVENT_SEARCH.getRedirectedUrl();
	}
}
