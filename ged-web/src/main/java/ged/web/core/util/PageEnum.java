package ged.web.core.util;

import ged.ejb.core.model.Identifiable;

public enum PageEnum {

	CANDIDATE("/candidate/view"), //
	CANDIDATE_ADD("/candidate/add"), //
	CANDIDATE_EDIT("/candidate/edit"), //
	CANDIDATE_SEARCH("/candidate/search"), //
	CANDIDATE_SELECT("/candidate/select"), //
	CLIENT("/client/view"), //
	CLIENT_ADD("/client/add"), //
	CLIENT_EDIT("/client/edit"), //
	CLIENT_SEARCH("/client/search"), //
	CONFIG("/config"), //
	CONTACT_ADD("/client/contact/add"), //
	CONTACT_EDIT("/client/contact/edit"), //
	CURRICULUM_EDIT("/candidate/curriculum/edit"), //
	INDEX("/index"), //
	ISABEL("/isabel/isabel"), //
	JOB("/job/view"), //
	JOB_ADD("/job/add"), //
	JOB_EDIT("/job/edit"), //
	JOB_SEARCH("/job/search"), //
	LOGIN("/login"), //
	MAINTENANCE("/maintenance/index"), //
	REPORT("/report/search"), //
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