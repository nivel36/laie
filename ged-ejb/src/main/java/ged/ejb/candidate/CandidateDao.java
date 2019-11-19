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
	
	protected boolean existUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(Boolean.class, "Candidate.existUid", map("uid", uid));
	}

	public JobOffer findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(JobOffer.class, "Candidate.findByUid", map("uid", uid));
	}

	public boolean emailExists(final String email) {
		Objects.requireNonNull(email);
		return this.findByQuery(Boolean.class, "Candidate.emailExists", map("email", email));
	}

	public List<Origin> findAllOrigins() {
		return this.getPersistenceFacade().findAll(Origin.class, Page.ALL);
	}

	public Candidate findCandidateByEmail(final String email) {
		Objects.requireNonNull(email);
		return this.findByQuery(Candidate.class, "Candidate.findByEmail", map("email", email));
	}

	public Candidate findCandidateData(final long candidateId) {
		if (candidateId < 1) {
			logger.error("Bad candidate id: {}", candidateId);
			throw new IllegalArgumentException("Bad candidate id " + candidateId);
		}
		return this.findByQuery(Candidate.class, "Candidate.findAllDataById", map("id", candidateId));
	}

	public List<Candidate> findCandidates(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		try {
			return this.findByQuery(Candidate.class, "Candidate.findByJobOffer", map("jobOffer", jobOffer), page);
		} catch (final NoResultException e) {
			return new ArrayList<>();
		}
	}

	@Override
	public Class<Candidate> getType() {
		return Candidate.class;
	}

	private boolean isDuplicatedEmail(final String emailToInsert, final String emailInRepository) {
		final boolean candidateHasChangedHisEmail = !emailToInsert.equals(emailInRepository);
		final boolean newEmailAlredyExists = this.emailExists(emailToInsert);
		return candidateHasChangedHisEmail && newEmailAlredyExists;
	}

	@Override
	protected void preInsert(final Candidate candidate) {
		if (this.emailExists(candidate.getEmail())) {
			logger.warn("The email {} is in use", candidate.getEmail());
			throw new ValidationException("Email duplicated");
		}
		final String base64Id = generateUid();
		candidate.setUid(base64Id);
	}

	@Override
	protected void preUpdate(final Candidate candidate) {
		final Candidate candidateInRepository = this.find(candidate.getId());
		if (this.isDuplicatedEmail(candidate.getEmail(), candidateInRepository.getEmail())) {
			logger.warn("The email {} is in use", candidate.getEmail());
			throw new ValidationException("Email duplicated");
		}
	}

	@Override
	public String[] searchFields() {
		return new String[] { "_name", "_surname", "_jobProfile", "tags.label" };
	}
}