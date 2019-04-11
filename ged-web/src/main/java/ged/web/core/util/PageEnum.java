package ged.web.core.util;

public enum PageEnum {

	CANDIDATE("/candidate/candidate"), //
	CANDIDATE_EDIT("/candidate/candidateEdit"), //
	CANDIDATE_SEARCH("/candidate/candidateSearch"), //
	CANDIDATE_SELECT("/candidate/candidateSelect"), //
	CLIENT("/client/client"), //
	CLIENT_SEARCH("/client/clientSearch"), //
	CONTACT("/client/contact"), //
	CONTACT_EDIT("/client/contactEdit"), //
	CURRICULUM("/candidate/curriculum"), //
	INDEX("/index"), //
	ISABEL("/isabel/isabel"), //
	JOB_OFFER("/jobOffer/jobOffer"), //
	JOB_OFFER_EDIT("/jobOffer/jobOfferEdit"), //
	JOB_OFFER_SEARCH("/jobOffer/jobOfferSearch"), LOGIN("/login"), //
	MAINTENANCE("/maintenance/maintenanceIndex"), //
	REPORT("/report/reportSearch"), //
	USER("/user/user"), //
	USER_EDIT("/user/userEdit"), //
	USER_SEARCH("/user/userSearch");

	private String url;

	PageEnum(final String url) {
		this.url = url;
	}

	public String url() {
		return this.url;
	}
}