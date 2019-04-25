package ged.web.core.util;

import ged.ejb.core.model.Identifiable;

public enum PageEnum {
	
	CANDIDATE("/candidate/candidate"), //
	CANDIDATE_EDIT("/candidate/candidateEdit"), //
	CANDIDATE_SEARCH("/candidate/candidateSearch"), //
	CANDIDATE_SELECT("/candidate/candidateSelect"), //
	CLIENT("/client/client"), //
	CLIENT_SEARCH("/client/clientSearch"), //
	CONTACT("/client/contact"), //
	CONTACT_EDIT("/client/contactEdit"), //
	CONFIG("/config"), //
	CURRICULUM("/candidate/curriculum"), //
	INDEX("/index"), //
	ISABEL("/isabel/isabel"), //
	JOB_OFFER("/jobOffer/jobOffer"), //
	JOB_OFFER_EDIT("/jobOffer/jobOfferEdit"), //
	JOB_OFFER_SEARCH("/jobOffer/jobOfferSearch"), //
	LOGIN("/login"), //
	MAINTENANCE("/maintenance/maintenanceIndex"), //
	REPORT("/report/reportSearch"), //
	USER("/user/view"), //
	USER_EDIT("/user/edit"), //
	USER_ADD("/user/add"), //
	USER_SEARCH("/user/search");

	private final static String REDIRECT = "faces-redirect=true";
	
	private String url;

	PageEnum(final String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}
	
	public String getUrl(Identifiable id) {
		return new StringBuilder(this.url).append("?id=").append(id.getId()).toString();
	}

	public String getRedirectUrl() {
		return new StringBuilder(this.url).append("?").append(REDIRECT).toString();
	}
	
	public String getRedirectUrl(Identifiable id) {
		return new StringBuilder(this.url).append("?").append(REDIRECT).append("&id=").append(id.getId()).toString();
	}
}