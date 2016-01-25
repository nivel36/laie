package ged.ejb.core.bookmark;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.user.User;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "userId",
		"auditedId", "entity" }) })
@NamedQueries(value = {
		@NamedQuery(name = "Bookmark.findByValues", query = "SELECT b FROM Bookmark b WHERE b.auditedId=:auditedId AND b.entity=:entity AND b.user=:user"),
		@NamedQuery(name = "Bookmark.findByUser", query = "SELECT b FROM Bookmark b WHERE b.user=:user") })
public class Bookmark extends AbstractEntity {

	private static final long serialVersionUID = 7897704476327486542L;

	@NotNull
	@Min(value = 1)
	@Column(nullable = false)
	private Long auditedId;

	@NotNull
	@Column(length = 256, nullable = false)
	private String entity;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false, updatable = false)
	private User user;

	public Long getAuditedId() {
		return auditedId;
	}

	public String getEntity() {
		return entity;
	}

	public User getUser() {
		return user;
	}

	public void setAuditedId(Long auditedId) {
		this.auditedId = auditedId;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((auditedId == null) ? 0 : auditedId.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bookmark other = (Bookmark) obj;
		if (auditedId == null) {
			if (other.auditedId != null)
				return false;
		} else if (!auditedId.equals(other.auditedId))
			return false;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bookmark [auditedId=" + auditedId + ", entity=" + entity
				+ ", user=" + user + "]";
	}
}
