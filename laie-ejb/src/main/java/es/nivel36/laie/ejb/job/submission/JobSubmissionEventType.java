package es.nivel36.laie.ejb.job.submission;

import es.nivel36.laie.ejb.core.EventType;

public enum JobSubmissionEventType implements EventType {

	EMAIL("email"), //
	MEETING("meeting"), //
	MESSAGE("message"), //
	OTHER("other"), //
	PHONE_CALL("phone_call"), //
	VIDEO_CALL("video_call");

	private final String name;

	JobSubmissionEventType(final String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}
}
