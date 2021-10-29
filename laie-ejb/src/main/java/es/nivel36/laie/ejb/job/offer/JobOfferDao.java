package es.nivel36.laie.ejb.job.offer;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.model.UidGenerator;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.core.util.Parameters;

@Repository
public class JobOfferDao extends AbstractDao {

	public void insert(final JobOffer jobffer) {
		Objects.requireNonNull(jobffer);
		this.setUid(null);
		this.em.persist(jobffer);
	}

	private void setUid(final JobOffer jobffer) {
		String uid;
		do {
			uid = UidGenerator.generate(JobOffer.class);
			jobffer.setUid(uid);
		} while (!this.checkDuplicateUid(uid));
	}

	private boolean checkDuplicateUid(final String uid) {
		final String namedQuery = "JobOffer.checkDuplicateUid";
		final Parameters parameters = map("uid", uid);
		return this.findByQuery(Boolean.class, namedQuery, parameters);
	}

	public JobOffer findByUid(final String uid) {
		Objects.requireNonNull(uid);
		final String namedQuery = "JobOffer.findByUid";
		final Parameters parameters = map("uid", uid);
		return this.findByQuery(JobOffer.class, namedQuery, parameters);
	}

	public JobOfferState findFirstJobOfferState() {
		final String namedQuery = "JobOfferState.findFirst";
		return this.findByQuery(JobOfferState.class, namedQuery, null);
	}

	public List<JobOffer> findJobOffersByCandidate(final String candidateUid, final Page page) {
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(page);
		final String namedQuery = "JobOffer.findByCandidate";
		final Parameters parameters = map("candidateUid", candidateUid);
		return this.findByQuery(JobOffer.class, namedQuery, parameters, page);
	}

	public List<JobOffer> findJobOffersByClient(final String clientUid, final Page page) {
		Objects.requireNonNull(clientUid);
		Objects.requireNonNull(page);
		final String namedQuery = "JobOffer.findByClient";
		final Parameters parameters = map("clientUid", clientUid);
		return this.findByQuery(JobOffer.class, namedQuery, parameters, page);
	}

	public List<JobOffer> findJobOffersByOwner(final String ownerUid, final Page page) {
		Objects.requireNonNull(ownerUid);
		Objects.requireNonNull(page);
		final String namedQuery = "JobOffer.findJobOffersByOwner";
		final Parameters parameters = map("ownerUid", ownerUid);
		return this.findByQuery(JobOffer.class, namedQuery, parameters, page);
	}

	public SearchResult<JobOffer> search(final String searchText, final Page page) {
		return this.search(searchText, page, null, null);
	}

	public SearchResult<JobOffer> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		final String[] searchFields = new String[] { "_title", "_client.name" };
		return this.search(JobOffer.class, page, sortOrder, searchFacets, searchText, searchFields);
	}
}
