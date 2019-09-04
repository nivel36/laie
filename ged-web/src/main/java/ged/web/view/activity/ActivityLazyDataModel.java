package ged.web.view.activity;

import java.util.Objects;

import ged.ejb.activity.Activity;
import ged.ejb.activity.ActivityService;
import ged.ejb.core.AbstractService;
import ged.web.core.view.AbstractLazyDataModel;

public class ActivityLazyDataModel extends AbstractLazyDataModel<Activity> {

	private static final long serialVersionUID = 1L;

	private transient ActivityService activityService;

	public ActivityLazyDataModel(final ActivityService activityService) {
		Objects.requireNonNull(activityService, "ActivityService can't be null");
		this.activityService = activityService;
	}

	@Override
	protected AbstractService<Activity> getService() {
		return activityService;
	}
}
