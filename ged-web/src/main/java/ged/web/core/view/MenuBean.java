package ged.web.core.view;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class MenuBean extends AbstractPageBean {

	private static final String CANDIDATE_URL = "/faces/candidate/candidateSearch?faces-redirect=true";
	private static final String CLIENT_URL = "/faces/client/clientSearch?faces-redirect=true";
	private static final String INDEX_URL = "/faces/index?faces-redirect=true";
	private static final String JOB_OFFERS_URL = "/faces/jobOffer/jobOfferSearch?faces-redirect=true";
	private static final String MAINTENANCES_URL = "/faces/maintenance/maintenanceIndex?faces-redirect=true";
	private static final String REPORTS_URL = "/faces/report/reportSearch?faces-redirect=true";
	private static final long serialVersionUID = 334119408975744799L;

	private static final String USERS_URL = "/faces/user/userSearch?faces-redirect=true";

	public String gotoCandidates() {
		return CANDIDATE_URL;
	}

	public String gotoClients() {
		return CLIENT_URL;
	}

	public String gotoIndex() {
		return INDEX_URL;
	}

	public String gotoJobOffers() {
		return JOB_OFFERS_URL;
	}

	public String gotoMaintenances() {
		return MAINTENANCES_URL;
	}

	public String gotoReports() {
		return REPORTS_URL;
	}

	public String gotoUsers() {
		return USERS_URL;
	}
}