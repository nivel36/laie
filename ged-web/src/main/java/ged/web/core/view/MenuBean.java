package ged.web.core.view;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class MenuBean extends AbstractPageBean {

	private static final long serialVersionUID = 334119408975744799L;

	public String gotoCandidates() {
		this.sessionBean.clearBreadcrumb();
		return "/faces/candidate/candidateSearch?faces-redirect=true";
	}

	public String gotoClients() {
		this.sessionBean.clearBreadcrumb();
		return "/faces/client/clientSearch?faces-redirect=true";
	}

	public String gotoIndex() {
		this.sessionBean.clearBreadcrumb();
		return "/faces/index?faces-redirect=true";
	}

	public String gotoJobOffers() {
		this.sessionBean.clearBreadcrumb();
		return "/faces/jobOffer/jobOfferSearch?faces-redirect=true";
	}

	public String gotoMaintenances() {
		this.sessionBean.clearBreadcrumb();
		return "/faces/maintenance/maintenanceIndex?faces-redirect=true";
	}

	public String gotoReports() {
		this.sessionBean.clearBreadcrumb();
		return "/faces/report/reportSearch?faces-redirect=true";
	}

	public String gotoUsers() {
		this.sessionBean.clearBreadcrumb();
		return "/faces/user/userSearch?faces-redirect=true";
	}
}
