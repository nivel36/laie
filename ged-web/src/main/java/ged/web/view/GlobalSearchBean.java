package ged.web.view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.util.MessageUtils;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class GlobalSearchBean extends AbstractBean {

	private static final long serialVersionUID = 8268523301916849175L;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobService;

	private String text;

	private List<User> users;

	@Inject
	private transient UserService userService;

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public String getText() {
		return this.text;
	}

	public List<User> getUsers() {
		return this.users;
	}

	@PostConstruct
	public void init() {
		this.users = new ArrayList<>();
		this.jobOffers = new ArrayList<>();
	}

	public void search() {
		if (this.text == null || this.text.length() < 3) {
			MessageUtils.addWarningMessage("error.search.camp_to_short", "error.search.camp_to_short");
		} else {
			this.users = this.userService.search(this.text);
			this.jobOffers = this.jobService.search(this.text);
		}
	}

	public void setJobService(final JobOfferService jobService) {
		this.jobService = jobService;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}