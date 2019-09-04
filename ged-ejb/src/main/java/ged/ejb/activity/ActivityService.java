package ged.ejb.activity;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;

@Stateless
public class ActivityService extends AbstractService<Activity> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private ActivityDao activityDao;
	
	@Inject
	@Repository
	private ActivityTypeDao activityTypeDao;

	@Override
	protected AbstractDao<Activity> getDao() {
		return activityDao;
	}
	
	public List<ActivityType> findActivityTypes() {
		logger.debug("Find all activity types");
		return activityTypeDao.findAll(Page.ALL);
	}
}
