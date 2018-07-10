package ged.web.view.job;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.GedPermissionException;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class JobOfferEditBean extends AbstractDialogBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -4373329969104383876L;

	private JobOffer jobOffer;

	@Inject
	private transient JobOfferService jobOfferService;

	private List<User> recruiters;

	@Inject
	private transient UserService userService;

	public void cleanClient() {
		this.jobOffer.setClient(new Client());
	}

	public void cleanOwner() {
		this.jobOffer.setOwner(null);
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public List<User> getRecruiters() {
		return this.recruiters;
	}

	@PostConstruct
	public void init() {
		logger.debug("JobOfferDialogBean init");
		this.jobOffer = this.getValueFromFlash("jobOffer");
		if ((this.jobOffer == null)) {
			this.jobOffer = new JobOffer();
		}
		if (this.isNewJobOffer()) {
			final User user = this.sessionBean.getUser();
			this.jobOffer.setOwner(user);
			final List<User> recruiters = this.userService.findSubordinateUsers(user);
			this.setRecruiters(recruiters);
		}
		else {
			this.recruiters = new ArrayList<>(this.jobOffer.getRecruiters());
		}
	}

	private void insertJobOffer() {
		this.jobOfferService.insert(this.jobOffer);
	}

	public boolean isNewJobOffer() {
		return this.jobOffer.getId() == 0;
	}

	public boolean isUserHasPermissionToEditJobOffer() {
		return this.userHasPermissionToEdit(this.jobOffer);
	}

	public void onCloseClientSearchDialog(final SelectEvent e) {
		final Client clientFromDialog = (Client) e.getObject();
		if (clientFromDialog != null) {
			this.jobOffer.setClient(clientFromDialog);
		}
	}

	public void openClientSearchDialog() {
		this.openBigDialog("/faces/client/clientSearchDialog", null);
	}

	public String save() {
		if (this.isNewJobOffer()) {
			this.insertJobOffer();
		}
		else {
			this.updateJobOffer();
		}
		return "/faces/jobOffer/jobOffer?faces-redirect=true&jobOfferId=" + this.jobOffer.getId();
	}

	public List<User> searchOwner(final String query) {
		if ((query == null) || (query.trim().length() < 3)) {
			return new ArrayList<>();
		}
		return this.userService.search(query);
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	public void setRecruiters(final List<User> recruiters) {
		this.recruiters = recruiters;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	private void updateJobOffer() {
		if (!this.isUserHasPermissionToEditJobOffer()) {
			throw new GedPermissionException();
		}
		this.jobOffer = this.jobOfferService.update(this.jobOffer);
	}
}