package ged.ejb.job.meeting;

import java.util.List;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Client;
import ged.ejb.core.AuditedService;
import ged.ejb.user.User;

public interface JobMeetingService extends AuditedService<JobMeeting> {
	
	List<JobMeeting> findByUser(final User user);
	
	List<JobMeeting> findByUserAndCandidate(final User user, final Candidate candidate);
	
	List<JobMeeting> findByUserAndClient(final User user, final Client client);
}