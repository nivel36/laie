package ged.ejb.candidate;

import static ged.ejb.core.util.Parameters.map;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.job.offer.JobOffer;

@Repository
public class CandidateDao extends AbstractIndexedDao<Candidate> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	public boolean emailExists(final String email) {
		Objects.requireNonNull(email);
		return this.findByQuery(Boolean.class, "Candidate.emailExists", map("email", email));
	}

	public List<Origin> findAllOrigins() {
		return this.getPersistenceFacade().findAll(Origin.class, Page.ALL_RESULTS);
	}

	public Candidate findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(Candidate.class, "Candidate.findByUid", map("uid", uid));
	}

	public List<Candidate> findCandidates(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		return this.findByQuery(Candidate.class, "Candidate.findByJobOffer", map("jobOffer", jobOffer), page);
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
		return new String[] { "_name", "_surname", "_jobProfile", "tags._label" };
	}
}