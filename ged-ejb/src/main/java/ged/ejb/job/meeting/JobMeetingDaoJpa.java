package ged.ejb.job.meeting;

import static ged.ejb.core.model.FluentHashMap.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Client;
import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class JobMeetingDaoJpa extends AbstractDaoJpa<JobMeeting> implements JobMeetingDao {

	@Override
	public List<JobMeeting> findByUser(final User user) {
		Objects.requireNonNull(user);
		return this.findByQuery(JobMeeting.class, "JobMeeting.findByUser", map("user", user), 0, 0);
	}

	@Override
	public List<JobMeeting> findByUserAndCandidate(final User user, final Candidate candidate) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(candidate);
		return this.findByQuery(JobMeeting.class, "JobMeeting.findByUserAndCandidate",
				map("user", user).and("candidate", candidate), 0, 0);
	}

	@Override
	public List<JobMeeting> findByUserAndClient(final User user, final Client client) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(client);
		return this.findByQuery(JobMeeting.class, "JobMeeting.findByUserAndClient",
				map("user", user).and("client", client), 0, 0);
	}

	@Override
	protected Class<JobMeeting> getType() {
		return JobMeeting.class;
	}

	@Override
	public List<JobMeeting> search(final String searchText) {
		throw new UnsupportedOperationException();
	}
}