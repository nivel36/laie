package ged.ejb.job.meeting;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Client;
import ged.ejb.core.AbstractAuditedService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

public class JobMeetingService extends AbstractAuditedService<JobMeeting> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private JobMeetingDao jobMeetingDao;

	public List<JobMeeting> findByUser(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Find job meetings by user {} in database", user);
		return this.jobMeetingDao.findByUser(user);
	}

	public List<JobMeeting> findByUserAndCandidate(final User user, final Candidate candidate) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(candidate);
		logger.debug("Find job meetings by user {} and candidate {} in database", user, candidate);
		return this.jobMeetingDao.findByUserAndCandidate(user, candidate);
	}

	public List<JobMeeting> findByUserAndClient(final User user, final Client client) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(client);
		logger.debug("Find job meetings by user {} and client {} in database", user, client);
		return this.jobMeetingDao.findByUserAndClient(user, client);
	}

	@Override
	protected AbstractDao<JobMeeting> getDao() {
		return this.jobMeetingDao;
	}

	public void setJobMeetingDao(final JobMeetingDao jobMeetingDao) {
		this.jobMeetingDao = jobMeetingDao;
	}
}
