package es.nivel36.laie.ejb.job.offer;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractIndexedDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;

@Repository
public class JobOfferDao extends AbstractIndexedDao<JobOffer> {

	public JobOffer findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(JobOffer.class, "JobOffer.findByUid", map("uid", uid));
	}

	public JobOfferState findFirstJobOfferState() {
		return this.findByQuery(JobOfferState.class, "JobOfferState.findFirst");
	}

	public List<JobOffer> findJobOffersByCandidate(final String candidateUid, final Page page) {
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(page);
		return this.findByQuery(JobOffer.class, "JobOffer.findByCandidate", map("candidateUid", candidateUid), page);
	}

	public List<JobOffer> findJobOffersByClientUid(final String clientUid, final Page page) {
		Objects.requireNonNull(clientUid);
		Objects.requireNonNull(page);
		return this.findByQuery(JobOffer.class, "JobOffer.findByClientUid", map("clientUid", clientUid), page);
	}

	public List<JobOffer> findJobOffersByUser(final String email, final Page page) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(page);
		return this.findByQuery(JobOffer.class, "JobOffer.findJobOffersByUser", map("email", email), page);
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
