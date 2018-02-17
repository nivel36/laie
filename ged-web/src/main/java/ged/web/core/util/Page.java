package ged.web.core.util;

public enum Page {

	CANDIDATE("/faces/candidate/candidate"),
	CANDIDATE_SEARCH("/faces/candidate/candidateSearch"),
	CLIENT("/faces/client/client"),
	CLIENT_SEARCH("/faces/client/clientSearch"),
	INDEX("/faces/index"),
	JOB_OFFER("/faces/jobOffer/jobOffer"),
	JOB_OFFER_SEARCH("/faces/jobOffer/jobOfferSearch"),
	LOGIN("/login"),
	USER("/faces/user/user"),
	USER_SEARCH("/faces/user/userSearch"),
	REPORT("/faces/report/reportSearch"),
	MAINTENANCE("/faces/maintenance/maintenanceSearch");

	private String url;

	Page(final String url) {
		this.url = url;
	}

	public String url() {
		return this.url;
	}
}