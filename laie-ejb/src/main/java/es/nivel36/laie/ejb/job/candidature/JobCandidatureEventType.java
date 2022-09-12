package es.nivel36.laie.ejb.job.candidature;

import es.nivel36.laie.ejb.core.EventType;

public enum JobCandidatureEventType implements EventType {

	EMAIL("email"), //
	MEETING("meeting"), //
	MESSAGE("message"), //
	OTHER("other"), //
	PHONE_CALL("phone_call"), //
	VIDEO_CALL("video_call");

	private String name;

	JobCandidatureEventType(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
