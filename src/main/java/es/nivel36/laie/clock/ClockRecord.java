package es.nivel36.laie.clock;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.core.service.AbstractEntity;
import es.nivel36.laie.user.User;

@Entity
public class ClockRecord extends AbstractEntity {

	private static final long serialVersionUID = 6200578389175178283L;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ClockType clockType;

	@NotNull
	private LocalDateTime timeEvent;

	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
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
