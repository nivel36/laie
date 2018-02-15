package ged.web.core.view;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ged.web.core.util.Navigate;

@Named
@RequestScoped
public class MenuBean extends AbstractBean {

	private static final long serialVersionUID = 334119408975744799L;

	public String gotoCandidates() {
		return Navigate.candidateSearchUrl();
	}

	public String gotoClients() {
		return Navigate.clientSearchUrl();
	}

	public String gotoIndex() {
		return Navigate.indexUrl();
	}

	public String gotoJobOffers() {
		return Navigate.jobOfferSearchUrl();
	}

	public String gotoMaintenances() {
		return Navigate.maintenancesUrl();
	}

	public String gotoReports() {
		return Navigate.reportsSearchUrl();
	}

	public String gotoUsers() {
		return Navigate.userSearchUrl();
	}
}