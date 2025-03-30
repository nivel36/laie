package es.nivel36.laie.ejb.job.submission;

public class StateEvent {

	private final JobSubmissionState state;

	private final String eventType;

	public StateEvent(final JobSubmissionState state, final String eventType) {
		this.state = state;
		this.eventType = eventType;
	}

	public JobSubmissionState getState() {
		return state;
	}

	public String getEventType() {
		return eventType;
	}
}