package ged.web.core.util;

import ged.ejb.core.model.Identifiable;

public enum PageEnum {

	CANDIDATE("/candidate/view"), //
	CANDIDATE_ADD("/candidate/add"), //
	CANDIDATE_EDIT("/candidate/edit", true), //
	CANDIDATE_SEARCH("/candidate/search"), //
	CANDIDATE_SELECT("/candidate/select"), //
	MEETING_SEARCH("/meeting/search"), //
	MEETING_ADD("/meeting/add"), //
	CHANGE_PASSWORD("/changePassword", true), //
	CLIENT("/client/view"), //
	CLIENT_ADD("/client/add"), //
	CLIENT_EDIT("/client/edit", true), //
	CLIENT_SEARCH("/client/search"), //
	CLIENT_SELECT("/client/select"), //
	CONFIG("/config"), //
	CONTACT_ADD("/client/contact/add"), //
	CONTACT_EDIT("/client/contact/edit", true), //
	CURRICULUM("/candidate/curriculum"), //
	CURRICULUM_JOB_EXPERIENCE("/candidate/curriculum/jobExperience", true), //
	CURRICULUM_LANGUAGE("/candidate/curriculum/language", true), //
	CURRICULUM_EDUCATION("/candidate/curriculum/education", true), //
	CURRICULUM_SKILLS("/candidate/curriculum/skills", true), //
	INDEX("/index"), //
	ISABEL("/isabel/isabel"), //
	JOB("/job/view"), //
	JOB_ADD("/job/add"), //
	JOB_EDIT("/job/edit", true), //
	JOB_SEARCH("/job/search"), //
	JOB_SELECT("/job/select"), //
	LOGIN("/login"), //
	MAINTENANCE("/maintenance"), //
	MAINTENANCE_JOB_CANDIDATURE_STATES("/maintenance/jobCandidatureStates"), //
	MAINTENANCE_JOB_OFFER_STATES("/maintenance/jobOfferStates"), //
	REPORT("/report/search"), //
	USER("/user/view"), //
	USER_ADD("/user/add"), //
	USER_EDIT("/user/edit", true), //
	USER_SEARCH("/user/search"), //
	USER_SELECT("/user/select");

	private static final String XHTML = ".xhtml";

	private final static String REDIRECT = "faces-redirect=true";

	private String url;

	private boolean isPost;

	PageEnum(final String url) {
		this(url, false);
	}

	PageEnum(final String url, final boolean isPost) {
		this.url = url + XHTML;
		this.isPost = isPost;
	}

	public String getUrl() {
		if (this.isPost) {
			return new StringBuilder(this.url).append("?").append(REDIRECT).toString();
		} else {
			return this.url;
		}
	}

	public String getUrl(final Identifiable id) {
		final StringBuilder urlBuilder = new StringBuilder(this.url).append("?id=").append(id.getId());
		if (this.isPost) {
			urlBuilder.append("&").append(REDIRECT);
		}
		return urlBuilder.toString();
	}
}