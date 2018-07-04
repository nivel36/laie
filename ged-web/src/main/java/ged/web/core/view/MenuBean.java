package ged.web.core.view;

import static ged.web.core.util.Page.CANDIDATE_SEARCH;
import static ged.web.core.util.Page.CLIENT_SEARCH;
import static ged.web.core.util.Page.INDEX;
import static ged.web.core.util.Page.ISABEL;
import static ged.web.core.util.Page.JOB_OFFER_SEARCH;
import static ged.web.core.util.Page.MAINTENANCE;
import static ged.web.core.util.Page.REPORT;
import static ged.web.core.util.Page.USER_SEARCH;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class MenuBean extends AbstractBean {

	private static final String FACES_REDIRECT = "?faces-redirect=true";

	private static final long serialVersionUID = 334119408975744799L;

	public String gotoCandidates() {
		return CANDIDATE_SEARCH.url() + FACES_REDIRECT;
	}

	public String gotoClients() {
		return CLIENT_SEARCH.url() + FACES_REDIRECT;
	}

	public String gotoIndex() {
		return INDEX.url() + FACES_REDIRECT;
	}

	public String gotoIsabel() {
		return ISABEL.url() + FACES_REDIRECT;
	}

	public String gotoJobOffers() {
		return JOB_OFFER_SEARCH.url() + FACES_REDIRECT;
	}

	public String gotoMaintenances() {
		return MAINTENANCE.url() + FACES_REDIRECT;
	}

	public String gotoReports() {
		return REPORT.url() + FACES_REDIRECT;
	}

	public String gotoUsers() {
		return USER_SEARCH.url() + FACES_REDIRECT;
	}
}