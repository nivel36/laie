package es.nivel36.laie.ejb.job.candidature;

public class StateEvent {
	
    private JobCandidatureState state;
    
    private String eventType;

    public StateEvent(JobCandidatureState state, String eventType) {
        this.state = state;
        this.eventType = eventType;
    }

    public JobCandidatureState getState() {
        return state;
    }

    public String getEventType() {
        return eventType;
    }
}