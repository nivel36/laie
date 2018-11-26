package ged.ejb.job.meeting;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Client;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class JobMeetingDao extends AbstractDao<JobMeeting> {

	public List<JobMeeting> findByOwner(final User owner) {
		Objects.requireNonNull(owner);
		return this.findByQuery(JobMeeting.class, "JobMeeting.findByOwner", map("owner", owner), Page.ALL);
	}

	public List<JobMeeting> findByOwnerAndCandidate(final User owner, final Candidate candidate) {
		Objects.requireNonNull(owner);
		Objects.requireNonNull(candidate);
		return this.findByQuery(JobMeeting.class, "JobMeeting.findByOwnerAndCandidate", map("owner", owner).and("candidate", candidate), Page.ALL);
	}

	public List<JobMeeting> findByOwnerAndClient(final User owner, final Client client) {
		Objects.requireNonNull(owner);
		Objects.requireNonNull(client);
		return this.findByQuery(JobMeeting.class, "JobMeeting.findByOwnerAndClient", map("owner", owner).and("client", client), Page.ALL);
	}

	@Override
	protected Class<JobMeeting> getType() {
		return JobMeeting.class;
	}

	@Override
	public List<JobMeeting> search(final String searchText, final Page page) {
		throw new UnsupportedOperationException();
	}
}