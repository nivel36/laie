package ged.ejb.core.action;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;

import ged.ejb.core.model.AbstractRecordEntity;

@Entity
public class Action extends AbstractRecordEntity {

	public enum ActionType {
		DELETE, LOGIN, SAVE, UNDELETE
	}

	private static final long serialVersionUID = -3095037007290696579L;

	@Column(length = 8)
	private String actionPerformed;

	private LocalDateTime date;

	public Action() {
	}

	public Action(final String entityClass, final long entityId, final String text) {
		super(entityClass, entityId, text);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Action other = (Action) obj;
		return super.equals(other) && Objects.equals(this.actionPerformed, other.actionPerformed) && Objects.equals(this.date, other.date);
	}

	public String getActionPerformed() {
		return this.actionPerformed;
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), this.actionPerformed, this.date);
	}

	public void setActionPerformed(final String actionPerformed) {
		this.actionPerformed = actionPerformed;
	}

	public void setDate(final LocalDateTime date) {
		this.date = date;
	}

}