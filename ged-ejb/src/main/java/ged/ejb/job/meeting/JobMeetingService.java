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

	public List<JobMeeting> findByOwner(final User owner) {
		Objects.requireNonNull(owner);
		logger.debug("Find job meetings by owner {} in database", owner);
		return this.jobMeetingDao.findByOwner(owner);
	}

	public List<JobMeeting> findByOwnerAndCandidate(final User owner, final Candidate candidate) {
		Objects.requireNonNull(owner);
		Objects.requireNonNull(candidate);
		logger.debug("Find job meetings by owner {} and candidate {} in database", owner, candidate);
		return this.jobMeetingDao.findByOwnerAndCandidate(owner, candidate);
	}

	public List<JobMeeting> findByOwnerAndClient(final User owner, final Client client) {
		Objects.requireNonNull(owner);
		Objects.requireNonNull(client);
		logger.debug("Find job meetings by owner {} and client {} in database", owner, client);
		return this.jobMeetingDao.findByOwnerAndClient(owner, client);
	}

	@Override
	protected AbstractDao<JobMeeting> getDao() {
		return this.jobMeetingDao;
	}

	public void setJobMeetingDao(final JobMeetingDao jobMeetingDao) {
		this.jobMeetingDao = jobMeetingDao;
	}
}
