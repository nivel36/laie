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
import ged.ejb.core.model.Page;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class JobOfferEditBean extends AbstractDialogBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -4373329969104383876L;

	private JobOffer jobOffer;

	@Inject
	private transient JobOfferService jobOfferService;

	private List<String> recruiters;

	@Inject
	private transient UserService userService;

	private void editJobOfferInit() {
		logger.debug("Edit job offer {} init", this.jobOffer);
		this.recruiters = new ArrayList<>();
		for (final User recruiter : this.jobOffer.getRecruiters()) {
			this.recruiters.add(recruiter.getFullName());
		}
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public List<String> getRecruiters() {
		return this.recruiters;
	}

	@PostConstruct
	public void init() {
		this.jobOffer = this.getValueFromFlash("jobOffer");
		if ((this.jobOffer == null)) {
			this.newJobOfferInit();
		}
		else {
			this.editJobOfferInit();
		}
	}

	public boolean isNewJobOffer() {
		return this.jobOffer.getId() == 0;
	}

	public boolean isUserHasPermissionToEditJobOffer() {
		return this.sessionUser.hasPermissionToEdit(this.jobOffer);
	}

	private void newJobOfferInit() {
		logger.debug("New job offer init");
		this.jobOffer = new JobOffer();
		final User user = this.setSessionUserAsOwner();
		this.setSubordinateUsersAsRecruiters(user);
		this.setClientFromFlash();
	}

	public void onCloseClientSearchDialog(final SelectEvent e) {
		final Client clientFromDialog = (Client) e.getObject();
		if (clientFromDialog != null) {
			this.jobOffer.setClient(clientFromDialog);
		}
	}

	public void openClientSearchDialog() {
		this.openBigDialog("/client/clientSearchDialog", null);
	}

	public String save() {
		if (this.isNewJobOffer()) {
			this.jobOffer = this.jobOfferService.create(this.jobOffer);
		}
		else {
			this.jobOffer = this.jobOfferService.save(this.jobOffer);
		}
		return "/jobOffer/jobOffer?faces-redirect=true&jobOfferId=" + this.jobOffer.getId();
	}

	public List<User> searchOwner(final String query) {
		if ((query == null) || (query.trim().length() < 3)) {
			return new ArrayList<>();
		}
		return this.userService.search(query, Page.ALL);
	}

	private void setClientFromFlash() {
		final Client client = this.getValueFromFlash("client");
		this.jobOffer.setClient(client);
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	public void setRecruiters(final List<String> recruiters) {
		this.recruiters = recruiters;
	}

	private User setSessionUserAsOwner() {
		final User user = this.sessionUser.get();
		this.jobOffer.setOwner(user);
		return user;
	}

	private void setSubordinateUsersAsRecruiters(final User user) {
		final List<User> subordinateUsers = this.userService.findSubordinateUsers(user);
		this.recruiters = new ArrayList<>();
		this.recruiters.add(this.sessionUser.get().getFullName());
		for (final User subordinate : subordinateUsers) {
			this.recruiters.add(subordinate.getFullName());
		}
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}