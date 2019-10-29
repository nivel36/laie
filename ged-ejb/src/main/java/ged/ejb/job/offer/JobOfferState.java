package ged.ejb.job.offer;

public enum JobOfferState {

	OPENED("job_offer_state.opened", "green"), //
	CREATED("job_offer_state.created", "cornflowerblue"), //
	CANCELLED("job_offer_state.cancelled", "brown"), //
	FINISHED("job_offer_state.finished", "blue"), //
	CLOSED("job_offer_state.closed", "red"), //
	PAUSED("job_offer_state.paused", "blueviolet");

	private String name;

	private String color;

	JobOfferState(String name, String color) {
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}
}
