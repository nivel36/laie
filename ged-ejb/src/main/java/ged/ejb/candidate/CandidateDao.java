package ged.ejb.candidate;

import static ged.ejb.core.util.Parameters.map;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.job.offer.JobOffer;

@Repository
public class CandidateDao extends AbstractDao<Candidate> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	public boolean emailExists(final String email) {
		Objects.requireNonNull(email);
		return this.findByQuery(Boolean.class, "Candidate.emailExists", map("email", email));
	}

	public List<Origin> findAllOrigins() {
		return getPersistenceFacade().findAll(Origin.class, Page.ALL);
	}

	public Candidate findCandidateData(final long candidateId) {
		if (candidateId < 1) {
			logger.warn("Bad candidate id: {}", candidateId);
			throw new IllegalArgumentException("Bad candidate id " + candidateId);
		}
		return this.findByQuery(Candidate.class, "Candidate.findAllDataById", map("id", candidateId));
	}

	public List<Candidate> findCandidates(final JobOffer jobOffer) {
		try {
			Objects.requireNonNull(jobOffer);
			return this.findByQuery(Candidate.class, "Candidate.findByJobOffer", map("jobOffer", jobOffer), Page.ALL);
		} catch (final NoResultException e) {
			logger.debug("No candidates found", e);
			return new ArrayList<>();
		}
	}

	public List<Candidate> findLastAddedCandidates(final int numberOfCandidates) {
		if (numberOfCandidates < 1) {
			logger.warn("Bad number of candidates {}", numberOfCandidates);
			throw new IllegalArgumentException("Bad number of candidates: " + numberOfCandidates);
		}
		return this.findByQuery(Candidate.class, "Candidate.findLastAddedCandidates", null,
				new Page(0, numberOfCandidates));
	}

	public long findNumberOfCandidates() {
		return (long) this.findByQuery("Candidate.numberOfCandidates", null);
	}

	@Override
	public Class<Candidate> getType() {
		return Candidate.class;
	}

	private boolean isDuplicatedEmail(final String emailToInsert, final String emailInRepository) {
		final boolean candidateHasChangedHisEmail = !emailToInsert.equals(emailInRepository);
		final boolean newEmailAlredyExists = emailExists(emailToInsert);
		return candidateHasChangedHisEmail && newEmailAlredyExists;
	}

	@Override
	protected void preInsert(final Candidate candidate) {
		if (emailExists(candidate.getEmail())) {
			logger.warn("The email {} is in use", candidate.getEmail());
			throw new ValidationException("Email duplicated");
		}
	}

	@Override
	protected void preUpdate(final Candidate candidate) {
		final Candidate candidateInRepository = find(candidate.getId());
		if (isDuplicatedEmail(candidate.getEmail(), candidateInRepository.getEmail())) {
			logger.warn("The email {} is in use", candidate.getEmail());
			throw new ValidationException("Email duplicated");
		}
	}

	@Override
	public String[] searchFields() {
		return new String[] { "name", "surname", "jobProfile", "tags.label" };
	}
}