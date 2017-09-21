package ged.ejb.job.meeting;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Client;
import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;
import static ged.ejb.core.model.QueryParameter.with;

@Repository
public class JobMeetingDaoJpa extends AbstractDaoJpa<JobMeeting> implements JobMeetingDao {

	@Inject
	public JobMeetingDaoJpa(final EntityManager em) {
		super(em);
	}

	@Override
	protected Class<JobMeeting> getType() {
		return JobMeeting.class;
	}

	@Override
	public List<JobMeeting> findByUser(User user) {
		Objects.requireNonNull(user);
		return this.findByTypedQuery(JobMeeting.class, "JobMeeting.findByUser", with("user", user).parameters(), 0, 0);
	}

	@Override
	public List<JobMeeting> findByUserAndCandidate(User user, Candidate candidate) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(candidate);
		return this.findByTypedQuery(JobMeeting.class, "JobMeeting.findByUserAndCandidate",
				with("user", user).and("candidate", candidate).parameters(), 0, 0);
	}

	@Override
	public List<JobMeeting> findByUserAndClient(User user, Client client) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(client);
		return this.findByTypedQuery(JobMeeting.class, "JobMeeting.findByUserAndClient",
				with("user", user).and("client", client).parameters(), 0, 0);
	}
}