package ged.ejb.candidate;

import static ged.ejb.core.util.Parameters.map;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.job.offer.JobOffer;

@Repository
public class CandidateDao extends AbstractDao<Candidate> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	
	public boolean emailExists(final String email) {
		Objects.requireNonNull(email);
		return this.findByQuery(Boolean.class, "Candidate.emailExists", map("email", email));
	}

	
	public Candidate findAllCandidateDataById(final long candidateId) {
		if (candidateId < 1) {
			logger.error("Bad candidate id {}", candidateId);
			throw new IllegalArgumentException("Bad candidate id " + candidateId);
		}
		return this.findByQuery(Candidate.class, "Candidate.findAllDataById", map("id", candidateId));
	}

	
	public List<Candidate> findCandidatesByJobOffer(final JobOffer jobOffer) {
		try {
			Objects.requireNonNull(jobOffer);
			return this.findByQuery(Candidate.class, "Candidate.findByJobOffer", map("jobOffer", jobOffer), 0, 0);
		}
		catch (final NoResultException e) {
			logger.debug("No candidates found", e);
			return new ArrayList<>();
		}
	}

	
	public List<Candidate> findLastAddedCandidates(final int numberOfCandidates) {
		if (numberOfCandidates < 1) {
			logger.error("Bad number of candidates {}", numberOfCandidates);
			throw new IllegalArgumentException("Bad number of candidates: " + numberOfCandidates);
		}
		return this.findByQuery(Candidate.class, "Candidate.findLastAddedCandidates", null, numberOfCandidates, null);
	}

	
	public long findNumberOfCandidates() {
		return (long) this.findByQuery("Candidate.numberOfCandidates", null);
	}

	
	@Override
	public Class<Candidate> getType() {
		return Candidate.class;
	}

	
	@Override
	public List<Candidate> search(final String searchText) {
		return this.getPersistenceFacade().search(Candidate.class, searchText, "name", "surname", "jobProfile", "tags.label");
	}
}