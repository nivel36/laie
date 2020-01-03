package ged.web.core.util;

import ged.ejb.core.model.Obfuscable;

public enum PageEnum {

	CANDIDATE("/candidate/view"), //
	CANDIDATE_ADD("/candidate/add"), //
	CANDIDATE_EDIT("/candidate/edit"), //
	CANDIDATE_SEARCH("/candidate/search"), //
	CANDIDATE_SELECT("/candidate/select"), //
	CHANGE_PASSWORD("/changePassword", true), //
	CLIENT("/client/view"), //
	CLIENT_ADD("/client/add"), //
	CLIENT_EDIT("/client/edit", true), //
	CLIENT_SEARCH("/client/search"), //
	CLIENT_SELECT("/client/select"), //
	CONFIG("/config"), //
	CONTACT("/client/contact/view"), //
	CONTACT_ADD("/client/contact/add"), //
	CONTACT_EDIT("/client/contact/edit", true), //
	CURRICULUM("/candidate/curriculum"), //
	CURRICULUM_EDUCATION("/candidate/curriculum/education", true), //
	CURRICULUM_JOB_EXPERIENCE("/candidate/curriculum/jobExperience", true), //
	CURRICULUM_LANGUAGE("/candidate/curriculum/language", true), //
	CURRICULUM_SKILLS("/candidate/curriculum/skills", true), //
	ERROR("/error"), //
	EVENT_ADD("/event/add"), //
	EVENT_SEARCH("/event/search"), //
	INDEX("/index"), //
	ISABEL("/isabel/isabel"), //
	JOB("/job/view"), //
	JOB_ADD("/job/add"), //
	JOB_EDIT("/job/edit", true), //
	JOB_EDIT_STATE("/job/editState", true), //
	JOB_PREVIEW("/job/preview"), //
	JOB_SEARCH("/job/search"), //
	JOB_SELECT("/job/select"), //
	OPEN_JOB("/open/jobOffer"),
	LOGIN("/login"), //
	MAINTENANCE("/maintenance"), //
	MAINTENANCE_GDPR("/maintenance/gdpr"), //
	MAINTENANCE_JOB_CANDIDATURE_STATES("/maintenance/jobCandidatureStates"), //
	MAINTENANCE_JOB_OFFER_STATES("/maintenance/jobOfferStates"), //
	MAINTENANCE_REPORTS("/maintenance/reports"), //
	MEETING_ADD("/meeting/add"), //
	MEETING_SEARCH("/meeting/search"), //
	REPORT("/report/search"), //
	USER("/user/view"), //
	USER_ADD("/user/add"), //
	USER_EDIT("/user/edit"), //
	USER_SEARCH("/user/search"), //
	USER_SELECT("/user/select");

	private final static String REDIRECT = "faces-redirect=true";

	private static final String XHTML = ".xhtml";

	private boolean isPost;

	private String url;

	PageEnum(final String url) {
		this(url, false);
	}

	PageEnum(final String url, final boolean isPost) {
		this.url = url + XHTML;
		this.isPost = isPost;
	}

	public String getRedirectedUrl() {
		return new StringBuilder(this.url).append("?").append(REDIRECT).toString();
	}

	public String getRedirectedUrl(final Obfuscable id) {
		return new StringBuilder(this.url).append("?id=").append(id.getUid()).append("&").append(REDIRECT).toString();
	}

	public String getUrl() {
		if (this.isPost) {
			return this.getRedirectedUrl();
		} else {
			return this.url;
		}
	}

	public String getUrl(final Obfuscable id) {
		if (this.isPost) {
			return this.getRedirectedUrl(id);
		} else {
			return new StringBuilder(this.url).append("?id=").append(id.getUid()).toString();
		}
	}
}
