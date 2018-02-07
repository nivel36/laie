package ged.web.core.view;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class MenuBean extends AbstractBean {

	public static final String CANDIDATES = "/faces/candidate/candidateSearch?faces-redirect=true";

	public static final String CLIENTS = "/faces/client/clientSearch?faces-redirect=true";

	public static final String INDEX = "/faces/index?faces-redirect=true";

	public static final String JOB_OFFERS = "/faces/jobOffer/jobOfferSearch?faces-redirect=true";

	public static final String MAINTENANCES = "/faces/maintenance/maintenanceIndex?faces-redirect=true";

	public static final String REPORTS = "/faces/report/reportSearch?faces-redirect=true";

	private static final long serialVersionUID = 334119408975744799L;

	public static final String USERS = "/faces/user/userSearch?faces-redirect=true";

	public String gotoCandidates() {
		return CANDIDATES;
	}

	public String gotoClients() {
		return CLIENTS;
	}

	public String gotoIndex() {
		return INDEX;
	}

	public String gotoJobOffers() {
		return JOB_OFFERS;
	}

	public String gotoMaintenances() {
		return MAINTENANCES;
	}

	public String gotoReports() {
		return REPORTS;
	}

	public String gotoUsers() {
		return USERS;
	}
}