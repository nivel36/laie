package ged.ejb.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import ged.ejb.user.User;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "userId", "text" }) })
@NamedQueries(value = {
		@NamedQuery(name = "SavedSearch.findByValues", query = "SELECT s FROM SavedSearch s WHERE s.text=:text AND s.user=:user"),
		@NamedQuery(name = "SavedSearch.findByUser", query = "SELECT s FROM SavedSearch s WHERE s.user=:user") })
public class SavedSearch extends AuditedEntity {

	private static final long serialVersionUID = -8985338998772713787L;

	@NotNull
	@Column(length = 128, nullable = false)
	private String text;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		SavedSearch other = (SavedSearch) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
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
		return "SavedSearch [text=" + text + ", user=" + user + "]";
	}
}
