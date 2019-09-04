package ged.ejb.activity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.user.User;

@Entity
public class Activity extends AbstractEntity {
	
	private static final long serialVersionUID = -4676990785364088633L;

	private LocalDateTime date;
	
	private String description;
	
	@ManyToOne
	private ActivityType type;

	@ManyToOne
	private User user;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		return Objects.equals(date, other.date) && Objects.equals(type, other.type) && Objects.equals(user, other.user);
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public ActivityType getType() {
		return type;
	}

	public User getUser() {
		return user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, type, user);
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setType(ActivityType type) {
		this.type = type;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
