package es.nivel36.laie.ejb.core.event;

import java.time.LocalDateTime;
import java.util.Objects;

import es.nivel36.laie.ejb.core.action.Auditable;
import es.nivel36.laie.ejb.user.User;

public abstract class AbstractEvent implements Event {
	
	/**
	 * La entidad sobre la que se ha generado el evento
	 */
	protected final Auditable entity;

	/**
	 * La hora del evento
	 */
	protected final LocalDateTime dateTime;

	/**
	 * El usuario que ha generado el evento
	 */protected final User user;

	public AbstractEvent(final Auditable entity, final User user) {
		Objects.requireNonNull(entity);
		Objects.requireNonNull(user);
		this.entity = entity;
		this.user = user;
		this.dateTime = LocalDateTime.now();
	}
	
	public Auditable getEntity() {
		return entity;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public User getUser() {
		return user;
	}
}
