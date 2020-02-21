package ged.ejb.job.offer;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Client;
import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class JobOfferDao extends AbstractIndexedDao<JobOffer> {

	public JobOffer findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(JobOffer.class, "JobOffer.findByUid", map("uid", uid));
	}

	public JobOfferState findFirstJobOfferState() {
		return this.findByQuery(JobOfferState.class, "JobOfferState.findFirst");
	}

	public List<JobOffer> findJobOffers(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate, "Candidate can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		return this.findByQuery(JobOffer.class, "JobOffer.findByCandidate", map("candidate", candidate), page);
	}

	public List<JobOffer> findJobOffers(final Client client, final Page page) {
		Objects.requireNonNull(client, "Client can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		return this.findByQuery(JobOffer.class, "JobOffer.findByClient", map("client", client), page);
	}

	public List<JobOffer> findJobOffers(final User owner, final Page page) {
		Objects.requireNonNull(owner, "Owner can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		return this.findByQuery(JobOffer.class, "JobOffer.findAllByOwner", map("owner", owner), page);
	}

	@Override
	public Class<JobOffer> getType() {
		return JobOffer.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "title", "client.name" };
	}
}
