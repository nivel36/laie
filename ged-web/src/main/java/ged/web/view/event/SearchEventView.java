package ged.web.view.event;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.event.EventService;
import ged.ejb.job.candidature.JobCandidatureState;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class SearchEventView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private EventLazyDataModel events;
	
	
	private JobCandidatureState searchState;

	public JobCandidatureState getSearchState() {
		return searchState;
	}

	public void setSearchState(JobCandidatureState searchState) {
		this.searchState = searchState;
	}

	@Inject
	protected transient EventService eventService;

	public void export() throws IOException {
		logger.debug("Export events action performed");
	}

	public EventLazyDataModel getEvents() {
		return events;
	}

	@PostConstruct
	public void init() {
		logger.trace("Search events init");
		events = initEvents();
	}

	public void search() {
		logger.debug("Search events action performed");
		if(searchState != null) {
			events.addSearchFilter("status.name", searchState.getName());
		}
	}

	private EventLazyDataModel initEvents() {
		return new EventLazyDataModel(eventService);
	}

	public void setEventService(final EventService eventService) {
		this.eventService = eventService;
	}

}
