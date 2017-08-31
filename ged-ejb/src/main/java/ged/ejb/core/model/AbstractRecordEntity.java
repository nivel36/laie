package ged.ejb.core.model;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import ged.ejb.core.action.ActionListener;
import ged.ejb.user.User;

@MappedSuperclass
@EntityListeners(ActionListener.class)
public class AbstractRecordEntity extends AbstractEntity {

	private static final long serialVersionUID = 8347072557418850004L;

	@Column(length = 64)
	private String entityClass;

	private long entityId;

	@Column(length = 128)
	private String text;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	public AbstractRecordEntity() {
	}

	public AbstractRecordEntity(final String entityClass, final long entityId, final String text) {
		this.entityClass = entityClass;
		this.entityId = entityId;
		this.text = text;
	}

	@Override
	public boolean equals(final Object obj) {
		if ( obj == null ){
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AbstractRecordEntity other = (AbstractRecordEntity) obj;
		if (this.entityClass == null) {
			if (other.entityClass != null) {
				return false;
			}
		} else if (!this.entityClass.equals(other.entityClass)) {
			return false;
		}
		if (this.entityId != other.entityId) {
			return false;
		}
		if (this.user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!this.user.equals(other.user)) {
			return false;
		}
		return true;
	}

	public String getEntityClass() {
		return this.entityClass;
	}

	public long getEntityId() {
		return this.entityId;
	}

	public String getText() {
		return this.text;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.entityClass == null ? 0 : this.entityClass.hashCode());
		result = prime * result + (int) this.entityId;
		result = prime * result + (this.user == null ? 0 : this.user.hashCode());
		return result;
	}

	public void setEntityClass(final String entityClass) {
		this.entityClass = entityClass;
	}

	public void setEntityId(final long entityId) {
		this.entityId = entityId;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return this.text;
	}
}