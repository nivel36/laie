package ged.ejb.service.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.GenericServiceImpl;

@Stateless
public class JobService extends GenericServiceImpl {

	@Inject
	private JobOfferDao jobOfferDao;

	private void addSearchProperty(final String name, final String value, final Map<String, Object> properties) {
		if ((value != null) && !value.trim().equals("")) {
			properties.put(name, value);
		}
	}

	public void insert(final JobOffer jobOffer) {
		this.jobOfferDao.insert(jobOffer);
	}

	public JobOffer searchById(final long id) {
		return getByPrimaryKey(JobOffer.class, id);
	}

	public List<JobOffer> searchByName(final String name) {
		final Map<String, Object> properties = new HashMap<String, Object>();
		addSearchProperty("name", name, properties);
		return getByProperties(JobOffer.class, properties, 10, 0);
	}

	public List<JobMeeting> searchConductedJobMeetingsByJobOffer(final JobOffer jobOffer) {
		List<JobMeeting> conductedJobMeetings = null;
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("jobOffer", jobOffer);
		conductedJobMeetings = getByTypedQuery(JobMeeting.class, "JobMeeting.getConductedJobMeetings", parameters);
		return conductedJobMeetings;
	}

	public List<JobMeeting> searchPlannedJobMeetingsByJobOffer(final JobOffer jobOffer) {
		List<JobMeeting> plannedJobMeetings = null;
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("jobOffer", jobOffer);
		plannedJobMeetings = getByTypedQuery(JobMeeting.class, "JobMeeting.getPlannedJobMeetings", parameters);
		return plannedJobMeetings;
	}

}
