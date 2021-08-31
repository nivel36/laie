package es.nivel36.laie.clock;

import java.time.LocalDateTime;
import java.util.Objects;

import es.nivel36.laie.user.User;

public class ClockRecord {

	enum ClockType {
		IN, OUT
	}

	private ClockType clockType;

	private LocalDateTime timeEvent;

	private User user;

	public ClockRecord() {
		// The default constructor
	}

	public ClockRecord(final ClockType clockType, final LocalDateTime timeEvent, final User user) {
		Objects.requireNonNull(clockType);
		Objects.requireNonNull(timeEvent);
		Objects.requireNonNull(user);
		this.clockType = clockType;
		this.timeEvent = timeEvent;
		this.user = user;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (this.getClass() != obj.getClass())) {
			return false;
		}
		final ClockRecord other = (ClockRecord) obj;
		return (this.clockType == other.clockType) && Objects.equals(this.timeEvent, other.timeEvent)
				&& Objects.equals(this.user, other.user);
	}

	public ClockType getClockType() {
		return this.clockType;
	}

	public LocalDateTime getTimeEvent() {
		return this.timeEvent;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.clockType, this.timeEvent, this.user);
	}

	public void setClockType(final ClockType clockType) {
		this.clockType = clockType;
	}

	public void setTimeEvent(final LocalDateTime timeEvent) {
		this.timeEvent = timeEvent;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ClockRecord [user=" + this.user + ", clockType=" + this.clockType + ", timeEvent=" + this.timeEvent
				+ "]";
	}

}
