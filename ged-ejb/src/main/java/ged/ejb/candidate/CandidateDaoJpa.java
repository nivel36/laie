package ged.ejb.candidate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;

import ged.ejb.core.model.AbstractDaoJpa;
import static ged.ejb.core.model.FluentHashMap.map;
import ged.ejb.core.model.Repository;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobOffer;

@Repository
public class CandidateDaoJpa extends AbstractDaoJpa<Candidate> implements CandidateDao {

	@Override
	public boolean emailExists(final String email) {
		Objects.requireNonNull(email);
		return this.findByQuery(Boolean.class, "Candidate.emailExists", map("email", email));
	}

	@Override
	public List<Candidate> findAllByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		final List<Candidate> candidates;
		try {
			candidates = this.findByQuery(Candidate.class, "Candidate.findAllByJobOffer", map("jobOffer", jobOffer), 0,
					0);
		} catch (final NoResultException e) {
			return new ArrayList<>();
		}
		return candidates;
	}

	@Override
	public List<Tag> findAllTags() {
		return this.findAll(Tag.class);
	}

	@Override
	public Candidate findCandidateAndFiles(final long id) {
		if (id < 1) {
			throw new IllegalArgumentException("id: " + id);
		}
		return this.findByQuery(Candidate.class, "Candidate.findCandidateAndFilesById", map("id", id));
	}

	@Override
	public List<Candidate> findLastAddedCandidates(final int numberOfCandidates) {
		if (numberOfCandidates < 1) {
			throw new IllegalArgumentException("numberOfCandidates: " + numberOfCandidates);
		}
		return this.findByQuery(Candidate.class, "Candidate.findLastAddedCandidates", null, numberOfCandidates, null);
	}

	@Override
	public long findNumberOfCandidates() {
		return (long) this.findByQuery("Candidate.numberOfCandidates", null);
	}

	@Override
	public Class<Candidate> getType() {
		return Candidate.class;
	}

	@Override
	public List<Candidate> search(final String searchText) {
		return getPersistenceFacade().search(Candidate.class, searchText, "name", "surname", "jobProfile",
				"tags.label");
	}
}