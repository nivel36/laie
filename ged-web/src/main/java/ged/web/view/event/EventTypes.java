package ged.web.view.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import ged.ejb.event.EventType;

@Named
@ApplicationScoped
public class EventTypes implements Serializable {

	private static final long serialVersionUID = 1L;

	private final List<EventType> listOfEventTypes = new ArrayList<>();

	public List<EventType> getList() {
		return this.listOfEventTypes;
	}

	@PostConstruct
	public void init() {
		for (final EventType eventType : EventType.values()) {
			this.listOfEventTypes.add(eventType);
		}
	}
}
