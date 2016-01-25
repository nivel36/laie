package ged.ejb.service.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import ged.ejb.core.GenericServiceImpl;

@Stateless
public class JobService extends GenericServiceImpl {

	public JobOffer searchById(long id) {
		return getByPrimaryKey(JobOffer.class, id);
	}

	public List<JobOffer> searchByName(String name) {
		Map<String, Object> properties = new HashMap<String, Object>();
		addSearchProperty("name", name, properties);
		return getByProperties(JobOffer.class, properties, 10, 0);
	}

	private void addSearchProperty(String name, String value,
			Map<String, Object> properties) {
		if (value != null && !value.trim().equals("")) {
			properties.put(name, value);
		}
	}

	public List<JobMeeting> searchPlannedJobMeetingsByJobOffer(JobOffer jobOffer) {
		List<JobMeeting> plannedJobMeetings = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("jobOffer", jobOffer);
		plannedJobMeetings = getByTypedQuery(JobMeeting.class,
				"JobMeeting.getPlannedJobMeetings", parameters);
		return plannedJobMeetings;
	}

	public List<JobMeeting> searchConductedJobMeetingsByJobOffer(
			JobOffer jobOffer) {
		List<JobMeeting> conductedJobMeetings = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("jobOffer", jobOffer);
		conductedJobMeetings = getByTypedQuery(JobMeeting.class,
				"JobMeeting.getConductedJobMeetings", parameters);
		return conductedJobMeetings;
	}

}
