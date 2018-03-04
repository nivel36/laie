package ged.web.core.view;

import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.CANDIDATE_SEARCH;
import static ged.web.core.util.Page.CLIENT_SEARCH;
import static ged.web.core.util.Page.INDEX;
import static ged.web.core.util.Page.JOB_OFFER_SEARCH;
import static ged.web.core.util.Page.MAINTENANCE;
import static ged.web.core.util.Page.REPORT;
import static ged.web.core.util.Page.USER_SEARCH;
import static ged.web.core.util.Page.ISABEL;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class MenuBean extends AbstractBean {

	private static final long serialVersionUID = 334119408975744799L;

	public String gotoCandidates() {
		return to(CANDIDATE_SEARCH).toUrl();
	}

	public String gotoClients() {
		return to(CLIENT_SEARCH).toUrl();
	}

	public String gotoIndex() {
		return to(INDEX).toUrl();
	}

	public String gotoJobOffers() {
		return to(JOB_OFFER_SEARCH).toUrl();
	}

	public String gotoMaintenances() {
		return to(MAINTENANCE).toUrl();
	}
	
	public String gotoReports() {
		return to(REPORT).toUrl();
	}

	public String gotoUsers() {
		return to(USER_SEARCH).toUrl();
	}
	
	public String gotoIsabel() {
		return to(ISABEL).toUrl();
	}
}