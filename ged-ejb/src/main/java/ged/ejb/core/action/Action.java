package ged.ejb.core.action;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ged.ejb.core.model.AbstractRecordEntity;

@Entity
public class Action extends AbstractRecordEntity {

	public enum ActionType {
		DELETE, LOGIN, SAVE, UNDELETE
	};

	private static final long serialVersionUID = -3095037007290696579L;

	@Column(length = 8)
	private String actionPerformed;

	@Temporal(TemporalType.TIME)
	private Date date;

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
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Action other = (Action) obj;
		return Objects.equals(this.actionPerformed, other.actionPerformed) && Objects.equals(this.date, other.date);
	}

	public String getActionPerformed() {
		return this.actionPerformed;
	}

	public Date getDate() {
		return this.date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.actionPerformed, this.date);
	}

	public void setActionPerformed(final String actionPerformed) {
		this.actionPerformed = actionPerformed;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

}