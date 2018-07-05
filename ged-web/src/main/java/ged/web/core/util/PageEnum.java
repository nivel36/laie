package ged.web.core.util;

public enum PageEnum {

	CANDIDATE("/faces/candidate/candidate"),
	CANDIDATE_EDIT("/faces/candidate/candidateEdit"),
	CANDIDATE_SEARCH("/faces/candidate/candidateSearch"),
	CANDIDATE_SELECT("/faces/candidate/candidateSelect"),
	CURRICULUM("/faces/candidate/curriculum"),
	CLIENT("/faces/client/client"),
	CLIENT_SEARCH("/faces/client/clientSearch"),
	CONTACT("/faces/client/contact"),
	CONTACT_EDIT("/faces/client/contactEdit"),
	INDEX("/faces/index"),
	JOB_OFFER("/faces/jobOffer/jobOffer"),
	JOB_OFFER_EDIT("/faces/jobOffer/jobOfferEdit"),
	JOB_OFFER_SEARCH("/faces/jobOffer/jobOfferSearch"),
	LOGIN("/login"),
	USER("/faces/user/user"),
	USER_EDIT("/faces/user/userEdit"),
	USER_SEARCH("/faces/user/userSearch"),
	REPORT("/faces/report/reportSearch"),
	MAINTENANCE("/faces/maintenance/maintenanceIndex"),
	ISABEL("/faces/isabel/isabel");

	private String url;

	PageEnum(final String url) {
		this.url = url;
	}

	public String url() {
		return this.url;
	}
}