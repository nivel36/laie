package es.nivel36.laie.web.view.event;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.event.JobCandidatureEventService;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class SearchEventView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private EventLazyDataModel events;

	@Inject
	protected transient JobCandidatureEventService jobCandidatureEventService;

	private String[] searchStates;

	public void export() {
		logger.debug("Export events action performed");
	}

	public EventLazyDataModel getEvents() {
		return this.events;
	}

	public String[] getSearchStates() {
		return this.searchStates;
	}

	@PostConstruct
	public void init() {
		logger.trace("Search events init");
		this.events = this.initEvents();
		this.search();
	}

	private EventLazyDataModel initEvents() {
		return new EventLazyDataModel();
	}

	public void search() {
		logger.debug("Search events action performed");
		this.events.clearSearchFilters();
		if ((this.getSearchStates() != null) && (this.getSearchStates().length > 0)) {
			this.events.addSearchFilter("state", "state.name", this.getSearchStates());
		}
	}

	public void setEventService(final JobCandidatureEventService jobCandidatureEventService) {
		this.jobCandidatureEventService = jobCandidatureEventService;
	}

	public void setSearchStates(final String[] searchStates) {
		this.searchStates = searchStates;
	}
}
