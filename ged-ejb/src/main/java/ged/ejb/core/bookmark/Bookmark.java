package ged.ejb.core.bookmark;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.user.User;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "userId", "entityClass", "entityId" }))
public class Bookmark extends AbstractEntity {

	private static final long serialVersionUID = 7897704476327486542L;

	@Column(length = 64)
	private String entityClass;

	private Long entityId;

	@Column(length = 128)
	private String text;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Bookmark other = (Bookmark) obj;
		if (this.entityClass == null) {
			if (other.entityClass != null) {
				return false;
			}
		} else if (!this.entityClass.equals(other.entityClass)) {
			return false;
		}
		if (this.entityId == null) {
			if (other.entityId != null) {
				return false;
			}
		} else if (!this.entityId.equals(other.entityId)) {
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

	public Long getEntityId() {
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
		result = (prime * result) + ((this.entityClass == null) ? 0 : this.entityClass.hashCode());
		result = (prime * result) + ((this.entityId == null) ? 0 : this.entityId.hashCode());
		result = (prime * result) + ((this.user == null) ? 0 : this.user.hashCode());
		return result;
	}

	public void setEntityClass(final String entityClass) {
		this.entityClass = entityClass;
	}

	public void setEntityId(final Long entityId) {
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
