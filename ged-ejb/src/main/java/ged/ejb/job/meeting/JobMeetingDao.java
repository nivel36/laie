package ged.ejb.job.meeting;

import java.util.List;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Client;
import ged.ejb.core.model.Dao;
import ged.ejb.user.User;

public interface JobMeetingDao extends Dao<JobMeeting> {

	List<JobMeeting> findByUser(final User user);
	
	List<JobMeeting> findByUserAndCandidate(final User user, final Candidate candidate);
	
	List<JobMeeting> findByUserAndClient(final User user, final Client client);
}