package es.nivel36.laie.ejb.permissions;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;

@Stateless
public class SecurityService {

	private @Inject AddClientPermission addClientPermission;

	private @Inject EditClientPermission editClientPermission;

	private @Inject AddCandidatePermission addCandidatePermission;

	private @Inject EditCandidatePermission editCandidatePermission;

	private @Inject AddCandidatureToJobOfferPermission addCandidatureToJobOfferPermission;

	private @Inject AddJobOfferPermission addJobOfferPermission;

	private @Inject EditJobOfferPermission editJobOfferPermission;

	public boolean canAddClient(final Client client, final User user) {
		return addClientPermission.validate(client, user);
	}

	public boolean canEditClient(final Client client, final User user) {
		return editClientPermission.validate(client, user);
	}

	public boolean canAddCandidate(final Candidate candidate, final User user) {
		return addCandidatePermission.validate(candidate, user);
	}

	public boolean canEditCandidate(final Candidate candidate, final User user) {
		return editCandidatePermission.validate(candidate, user);
	}

	public boolean canAddCandidature(final JobOffer jobOffer, final User user) {
		return addCandidatureToJobOfferPermission.validate(jobOffer, user);
	}

	public boolean canAddJobOffer(final JobOffer jobOffer, final User user) {
		return addJobOfferPermission.validate(jobOffer, user);
	}

	public boolean canEditJobOffer(final JobOffer jobOffer, final User user) {
		return editJobOfferPermission.validate(jobOffer, user);
	}
}