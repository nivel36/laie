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

	@Inject
	protected transient EventService eventService;

	private JobCandidatureState searchState;

	public void export() throws IOException {
		logger.debug("Export events action performed");
	}

	public EventLazyDataModel getEvents() {
		return this.events;
	}

	public JobCandidatureState getSearchState() {
		return this.searchState;
	}

	@PostConstruct
	public void init() {
		logger.trace("Search events init");
		this.events = this.initEvents();
		this.search();
	}

	private EventLazyDataModel initEvents() {
		return new EventLazyDataModel(this.eventService);
	}

	public void search() {
		logger.debug("Search events action performed");
		this.events.clearSearchFilters();
		if(getSearchState() != null ) {
			this.events.addSearchFilter("status", "status.name", getSearchState().getName());
		}
	}

	public void setEventService(final EventService eventService) {
		this.eventService = eventService;
	}

	public void setSearchState(final JobCandidatureState searchState) {
		this.searchState = searchState;
	}
}
