package ged.web.core.util;

public enum PageEnum {

	CANDIDATE("/candidate/view"), //
	CANDIDATE_ADD("/candidate/add"), //
	CANDIDATE_EDIT("/candidate/edit"), //
	CANDIDATE_SEARCH("/candidate/search"), //
	CANDIDATE_SELECT("/candidate/select"), //
	CHANGE_PASSWORD("/changePassword"), //
	CLIENT("/client/view"), //
	CLIENT_ADD("/client/add"), //
	CLIENT_EDIT("/client/edit"), //
	CLIENT_SEARCH("/client/search"), //
	CLIENT_SELECT("/client/select"), //
	CONFIG("/config"), //
	CONTACT("/client/contact/view"), //
	CONTACT_ADD("/client/contact/add"), //
	CONTACT_EDIT("/client/contact/edit"), //
	CURRICULUM("/candidate/curriculum"), //
	CURRICULUM_EDUCATION("/candidate/curriculum/education"), //
	CURRICULUM_JOB_EXPERIENCE("/candidate/curriculum/jobExperience"), //
	CURRICULUM_LANGUAGE("/candidate/curriculum/language"), //
	CURRICULUM_SKILLS("/candidate/curriculum/skills"), //
	ERROR("/error"), //
	EVENT_ADD("/event/add"), //
	EVENT_SEARCH("/event/search"), //
	INDEX("/index"), //
	ISABEL("/isabel/isabel"), //
	JOB("/job/view"), //
	JOB_ADD("/job/add"), //
	JOB_EDIT("/job/edit"), //
	JOB_EDIT_STATE("/job/editState"), //
	JOB_PREVIEW("/job/preview"), //
	JOB_SEARCH("/job/search"), //
	JOB_SELECT("/job/select"), //
	JOB_SELECT_CANDIDATES("/job/selectCandidates"), //
	OPEN_JOB("/open/jobOffer"), //
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

	private static final String XHTML = ".xhtml";

	private String url;

	PageEnum(final String url) {
		this.url = url + XHTML;
	}
	
	public String getUrl() {
		return this.url;
	}
}
