package ged.web.core.view;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class MenuBean extends AbstractPageBean {

	private static final long serialVersionUID = 334119408975744799L;

	public String gotoCandidates() {
		return "/faces/candidate/candidateSearch?faces-redirect=true";
	}

	public String gotoClients() {
		return "/faces/client/clientSearch?faces-redirect=true";
	}

	public String gotoIndex() {
		return "/faces/index?faces-redirect=true";
	}

	public String gotoJobOffers() {
		return "/faces/jobOffer/jobOfferSearch?faces-redirect=true";
	}

	public String gotoMaintenances() {
		return "/faces/maintenance/maintenanceIndex?faces-redirect=true";
	}

	public String gotoReports() {
		return "/faces/report/reportSearch?faces-redirect=true";
	}

	public String gotoUsers() {
		return "/faces/user/userSearch?faces-redirect=true";
	}
}
