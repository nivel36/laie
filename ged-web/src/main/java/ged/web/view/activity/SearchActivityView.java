package ged.web.view.activity;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.activity.ActivityService;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class SearchActivityView extends AbstractView {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private ActivityLazyDataModel activities;

	@Inject
	protected transient ActivityService activityService;

	public void export() throws IOException {
		logger.debug("Export activities action performed");
	}

	public ActivityLazyDataModel getActivities() {
		return activities;
	}

	@PostConstruct
	public void init() {
		logger.trace("Search activities init");
		activities = initActivities();
	}

	private ActivityLazyDataModel initActivities() {
		return new ActivityLazyDataModel(activityService);
	}

	public void setActivityService(final ActivityService activityService) {
		this.activityService = activityService;
	}

}
