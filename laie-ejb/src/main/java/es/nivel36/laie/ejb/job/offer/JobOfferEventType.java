package es.nivel36.laie.ejb.job.offer;

import es.nivel36.laie.ejb.core.EventType;

public enum JobOfferEventType implements EventType {

	AUTOMATIC_EVENT("automatic_event"), //
	MANUAL_EVENT("manual_event");

	private String name;

	JobOfferEventType(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
