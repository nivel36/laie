package ged.web.core.util;

public enum PageEnum {

	CANDIDATE("/faces/candidate/candidate"), //
	CANDIDATE_EDIT("/faces/candidate/candidateEdit"), //
	CANDIDATE_SEARCH("/faces/candidate/candidateSearch"), //
	CANDIDATE_SELECT("/faces/candidate/candidateSelect"), //
	CLIENT("/faces/client/client"), //
	CLIENT_SEARCH("/faces/client/clientSearch"), //
	CONTACT("/faces/client/contact"), //
	CONTACT_EDIT("/faces/client/contactEdit"), //
	CURRICULUM("/faces/candidate/curriculum"), //
	INDEX("/faces/index"), //
	ISABEL("/faces/isabel/isabel"), //
	JOB_OFFER("/faces/jobOffer/jobOffer"), //
	JOB_OFFER_EDIT("/faces/jobOffer/jobOfferEdit"), //
	JOB_OFFER_SEARCH("/faces/jobOffer/jobOfferSearch"), LOGIN("/login"), //
	MAINTENANCE("/faces/maintenance/maintenanceIndex"), //
	REPORT("/faces/report/reportSearch"), //
	USER("/faces/user/user"), //
	USER_EDIT("/faces/user/userEdit"), //
	USER_SEARCH("/faces/user/userSearch");

	private String url;

	PageEnum(final String url) {
		this.url = url;
	}

	public String url() {
		return this.url;
	}
}