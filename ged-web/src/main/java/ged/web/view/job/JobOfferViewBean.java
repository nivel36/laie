package ged.web.view.job;

import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.JOB_OFFER_SEARCH;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.client.ClientService;
import ged.ejb.job.meeting.JobMeeting;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.view.AbstractBean;
import ged.web.view.candidate.CandidateSelecteable;
import ged.web.view.candidate.SelectCandidatesAction;

@Named
@ViewScoped
public class JobOfferViewBean extends AbstractBean implements CandidateSelecteable {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -1200840678252895578L;

	private List<Candidate> candidates = new ArrayList<>();

	@Inject
	private transient CandidateService candidateService;

	@Inject
	private transient ClientService clientService;

	private List<JobMeeting> conductedJobMeetings = new ArrayList<>();

	private boolean editable;

	private JobOffer jobOffer;

	private String jobOfferId;

	@Inject
	private transient JobOfferService jobService;

	private final SelectCandidatesAction miCallback = new SelectCandidatesAction(this);

	private boolean newClient;

	private List<JobMeeting> plannedJobMeetings = new ArrayList<>();

	public void addJobCandidature(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		this.jobService.addJobCandidature(this.jobOffer, candidate);
	}

	public void cancel() {
		logger.debug("Cancel edit job offer action performed");
		this.editable = false;
	}

	public void clientChangedListener() {
		final String clientName = this.jobOffer.getClient().getName();
		logger.trace("CLIENT name changed to {}", clientName);
		this.newClient = !this.clientService.clientExist(clientName);
	}

	public void edit() {
		logger.debug("Edit job offer action performed");
		this.editable = true;
	}

	private void error() {
		to(JOB_OFFER_SEARCH).doPost();
	}

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public List<JobMeeting> getConductedJobMeetings() {
		return this.conductedJobMeetings;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public String getJobOfferId() {
		return this.jobOfferId;
	}

	public SelectCandidatesAction getMiCallback() {
		return this.miCallback;
	}

	public List<JobMeeting> getPlannedJobMeetings() {
		return this.plannedJobMeetings;
	}

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		if (this.jobOfferId != null) {
			try {
				final long id = Long.parseLong(this.jobOfferId);
				this.jobOffer = this.jobService.find(id);
				if (this.jobOffer == null) {
					error();
				} else {
					populateJobMeetings();
					this.candidates = this.candidateService.findAllByJobOffer(this.jobOffer);
				}
				this.editable = false;
			} catch (final NumberFormatException ex) {
				error();
			}
		} else {
			error();
		}
	}

	public boolean isEditable() {
		return this.editable;
	}

	public boolean isNewClient() {
		return this.newClient;
	}

	// boolean -> is[name]
	public boolean isUserHasPermissionToEditJobOffer() {
		final User jobOfferOwner = this.jobOffer.getOwner();
		final User user = this.sessionBean.getUser();
		if (jobOfferOwner.equals(user)) {
			return true;
		}
		return user.isAdmin() || user.isRecruiterAdmin();
	}

	@Override
	public void onCandidatesSelect(final List<Candidate> selectedCandidates) {
		for (final Candidate candidate : selectedCandidates) {
			this.jobService.addJobCandidature(this.jobOffer, candidate);
		}
		this.candidates.addAll(selectedCandidates);
	}

	private void populateJobMeetings() {
		this.plannedJobMeetings = null;
		this.conductedJobMeetings = null;
	}

	public void removeCandidate(final Candidate candidate) {
		this.candidates.remove(candidate);
		this.jobService.removeJobCandidature(this.jobOffer, candidate);
	}

	public void save() {
		logger.debug("Save job offer action performed");
		this.jobOffer = this.jobService.update(this.jobOffer);
		this.editable = false;
	}

	public void setCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		if (this.candidates.contains(candidate)) {
			return;
		}
		this.candidates.add(candidate);
		this.jobService.addJobCandidature(this.jobOffer, candidate);
	}

	public void setCandidates(final List<Candidate> candidates) {
		this.candidates = candidates;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

	public void setConductedJobMeetings(final List<JobMeeting> conductedJobMeetings) {
		this.conductedJobMeetings = conductedJobMeetings;
	}

	public void setJobMeeting(final JobMeeting jobMeeting) {
		Objects.requireNonNull(jobMeeting);
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobOfferId(final String jobOfferId) {
		this.jobOfferId = jobOfferId;
	}

	public void setJobService(final JobOfferService jobService) {
		this.jobService = jobService;
	}

	public void setPlannedJobMeetings(final List<JobMeeting> plannedJobMeetings) {
		this.plannedJobMeetings = plannedJobMeetings;
	}
}