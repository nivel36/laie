package ged.web.view;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.job.JobOffer;
import ged.ejb.job.JobService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractPageBean;
import ged.web.core.view.Paginator;

@Named
@ViewScoped
public class GlobalSearchBean extends AbstractPageBean {

	private static final long serialVersionUID = 8268523301916849175L;

	private Paginator<JobOffer> jobOfferPaginator;

	@Inject
	private JobService jobService;

	private String text;

	private Paginator<User> userPaginator;

	@Inject
	private UserService userService;

	public Paginator<JobOffer> getJobOfferPaginator() {
		return this.jobOfferPaginator;
	}

	public String getText() {
		return this.text;
	}

	public Paginator<User> getUserPaginator() {
		return this.userPaginator;
	}

	@PostConstruct
	public void init() {
		this.userPaginator = new Paginator<User>(this.sessionBean.getRowsPerPage());
		this.jobOfferPaginator = new Paginator<JobOffer>(this.sessionBean.getRowsPerPage());
	}

	public void search() {
		if ((this.text == null) || (this.text.length() < 3)) {
			addMessage(FacesMessage.SEVERITY_WARN, "error.search.camp_to_short", "error.search.camp_to_short");
		} else {
			this.userPaginator.setEntities(this.userService.fullSearch(this.text));
			this.jobOfferPaginator.setEntities(this.jobService.fullSearch(this.text));
		}
	}

	public void setText(final String text) {
		this.text = text;
	}
}
