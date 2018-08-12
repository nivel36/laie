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
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

public class JobMeetingServiceImpl extends AbstractAuditedService<JobMeeting> implements JobMeetingService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final JobMeetingDao jobMeetingDao;

	@Inject
	public JobMeetingServiceImpl(@Repository final JobMeetingDao jobMeetingDao) {
		this.jobMeetingDao = jobMeetingDao;
	}

	@Override
	public List<JobMeeting> findByUser(User user) {
		Objects.requireNonNull(user);
		logger.debug("Find job meetings by user {} in database", user);
		return jobMeetingDao.findByUser(user);
	}

	@Override
	public List<JobMeeting> findByUserAndCandidate(User user, Candidate candidate) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(candidate);
		logger.debug("Find job meetings by user {} and candidate {} in database", user, candidate);
		return jobMeetingDao.findByUserAndCandidate(user, candidate);
	}

	@Override
	public List<JobMeeting> findByUserAndClient(User user, Client client) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(client);
		logger.debug("Find job meetings by user {} and client {} in database", user, client);
		return jobMeetingDao.findByUserAndClient(user, client);
	}

	@Override
	protected Dao<JobMeeting> getDao() {
		return jobMeetingDao;
	}
}
