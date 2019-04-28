package ged.web.core.util;

import ged.ejb.core.model.Identifiable;

public enum PageEnum {

	CANDIDATE("/candidate/candidate"), //
	CANDIDATE_EDIT("/candidate/candidateEdit"), //
	CANDIDATE_SEARCH("/candidate/candidateSearch"), //
	CANDIDATE_SELECT("/candidate/candidateSelect"), //
	CLIENT("/client/view"), //
	CLIENT_ADD("/client/add"), //
	CLIENT_EDIT("/client/edit"), //
	CLIENT_SEARCH("/client/search"), //
	CONFIG("/config"), //
	CONTACT_ADD("/client/contact/add"), //
	CONTACT_EDIT("/client/contact/edit"), //
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
	USER_ADD("/user/add"), //
	USER_EDIT("/user/edit"), //
	USER_SEARCH("/user/search");

	private final static String REDIRECT = "faces-redirect=true";

	private String url;

	PageEnum(final String url) {
		this.url = url;
	}

	public String getRedirectUrl() {
		return new StringBuilder(this.url).append("?").append(REDIRECT).toString();
	}

	public String getRedirectUrl(final Identifiable id) {
		return new StringBuilder(this.url).append("?").append(REDIRECT).append("&id=").append(id.getId()).toString();
	}

	public String getUrl() {
		return this.url;
	}

	public String getUrl(final Identifiable id) {
		return new StringBuilder(this.url).append("?id=").append(id.getId()).toString();
	}
}