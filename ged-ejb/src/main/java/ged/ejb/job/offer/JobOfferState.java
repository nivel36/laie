package ged.ejb.job.offer;

import ged.ejb.core.EventState;

public enum JobOfferState implements EventState {

	CLOSED("closed", false, true), //
	CREATED("created", true, false), //
	FINISHED("finished", false, true), //
	OPENED("opened"), //
	PAUSED("paused");

	private boolean closeState;

	private String name;

	private boolean openState;

	JobOfferState(final String name) {
		this(name, false, false);
	}

	JobOfferState(final String name, final boolean openState, final boolean closeState) {
		this.name = name;
		this.openState = openState;
		this.closeState = closeState;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public boolean isCloseState() {
		return this.closeState;
	}

	public boolean isOpenState() {
		return this.openState;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
