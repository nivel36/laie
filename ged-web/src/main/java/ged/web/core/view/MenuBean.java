package ged.web.core.view;

import static ged.web.core.util.PageEnum.CANDIDATE_SEARCH;
import static ged.web.core.util.PageEnum.CLIENT_SEARCH;
import static ged.web.core.util.PageEnum.INDEX;
import static ged.web.core.util.PageEnum.ISABEL;
import static ged.web.core.util.PageEnum.JOB_OFFER_SEARCH;
import static ged.web.core.util.PageEnum.MAINTENANCE;
import static ged.web.core.util.PageEnum.REPORT;
import static ged.web.core.util.PageEnum.USER_SEARCH;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class MenuBean extends AbstractBean {

	private static final String FACES_REDIRECT = "?faces-redirect=true";

	private static final long serialVersionUID = 334119408975744799L;

	public String gotoCandidates() {
		return CANDIDATE_SEARCH.getUrl() + FACES_REDIRECT;
	}

	public String gotoClients() {
		return CLIENT_SEARCH.getUrl() + FACES_REDIRECT;
	}

	public String gotoIndex() {
		return INDEX.getUrl() + FACES_REDIRECT;
	}

	public String gotoIsabel() {
		return ISABEL.getUrl() + FACES_REDIRECT;
	}

	public String gotoJobOffers() {
		return JOB_OFFER_SEARCH.getUrl() + FACES_REDIRECT;
	}

	public String gotoMaintenances() {
		return MAINTENANCE.getUrl() + FACES_REDIRECT;
	}

	public String gotoReports() {
		return REPORT.getUrl() + FACES_REDIRECT;
	}

	public String gotoUsers() {
		return USER_SEARCH.getUrl() + FACES_REDIRECT;
	}
}