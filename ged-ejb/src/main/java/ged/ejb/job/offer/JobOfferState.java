package ged.ejb.job.offer;

public enum JobOfferState {

	CLOSED("job_offer_state.closed", "red", false, true), //
	CREATED("job_offer_state.created", "cornflowerblue", true, false), //
	FINISHED("job_offer_state.finished", "blue", false, true), //
	OPENED("job_offer_state.opened", "green"), //
	PAUSED("job_offer_state.paused", "blueviolet");

	private boolean closeState;

	private String color;

	private String name;

	private boolean openState;

	JobOfferState(final String name, final String color) {
		this(name, color, false, false);
	}

	JobOfferState(final String name, final String color, final boolean openState, final boolean closeState) {
		this.name = name;
		this.color = color;
		this.openState = openState;
		this.closeState = closeState;
	}

	public String getColor() {
		return this.color;
	}

	public String getName() {
		return this.name;
	}

	public boolean isCloseState() {
		return this.closeState;
	}

	public boolean isOpenState() {
		return this.openState;
	}
}
