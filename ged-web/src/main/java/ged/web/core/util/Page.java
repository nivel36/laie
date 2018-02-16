package ged.web.core.util;

public enum Page {

	CANDIDATE("/faces/candidate/candidate?id="),
	CANDIDATE_SEARCH("/faces/candidate/candidateSearch?faces-redirect=true"),
	CLIENT("/faces/client/client?id="),
	CLIENT_SEARCH("/faces/client/clientSearch?faces-redirect=true"),
	INDEX("/faces/index?faces-redirect=true"),
	JOB_OFFER("/faces/jobOffer/jobOffer?id="),
	JOB_OFFER_SEARCH("/faces/jobOffer/jobOfferSearch?faces-redirect=true"),
	LOGIN("/login?faces-redirect=true"),
	USER("/faces/user/user?id="),
	USER_SEARCH("/faces/user/userSearch?faces-redirect=true"),
	REPORT("/faces/report/reportSearch?faces-redirect=true"),
	MAINTENANCE("/faces/maintenance/maintenanceSearch?faces-redirect=true");

	private String url;

	Page(final String url) {
		this.url = url;
	}

	public String url() {
		return this.url;
	}
}