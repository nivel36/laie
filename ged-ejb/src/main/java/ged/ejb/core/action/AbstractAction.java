package ged.ejb.core.action;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.model.Ownerable;
import ged.ejb.user.User;

@MappedSuperclass
public abstract class AbstractAction extends AbstractEntity {

	private static final long serialVersionUID = -3095037007290696579L;

	private LocalDateTime instant;
	
	private String message;

	private User user;

	public AbstractAction() {
		instant = LocalDateTime.now();
	}

	public LocalDateTime getInstant() {
		return instant;
	}

	public String getMessage() {
		return message;
	}

	abstract Ownerable getObject();

	public User getUser() {
		return user;
	}

	public void setInstant(LocalDateTime instant) {
		this.instant = instant;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setUser(User user) {
		this.user = user;
	}

}