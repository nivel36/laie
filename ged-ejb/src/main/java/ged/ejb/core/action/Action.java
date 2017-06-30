package ged.ejb.core.action;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ged.ejb.core.model.AbstractRecordEntity;

@Entity
public class Action extends AbstractRecordEntity {

	public static final String DELETE = "DELETE";

	public static final String INSERT = "INSERT";

	private static final long serialVersionUID = -3095037007290696579L;

	public static final String UNDELETE = "UNDELETE";

	public static final String UPDATE = "UPDATE";

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
		if (this.actionPerformed == null) {
			if (other.actionPerformed != null) {
				return false;
			}
		} else if (!this.actionPerformed.equals(other.actionPerformed)) {
			return false;
		}
		if (this.date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!this.date.equals(other.date)) {
			return false;
		}
		return true;
	}

	public String getActionPerformed() {
		return this.actionPerformed;
	}

	public Date getDate() {
		return this.date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.actionPerformed == null ? 0 : this.actionPerformed.hashCode());
		result = prime * result + (this.date == null ? 0 : this.date.hashCode());
		return result;
	}

	public void setActionPerformed(final String actionPerformed) {
		this.actionPerformed = actionPerformed;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

}