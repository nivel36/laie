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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "userId", "url" }))
public class Bookmark extends AbstractEntity {

	private static final long serialVersionUID = 7897704476327486542L;

	@Column(length = 128)
	private String text;

	@Column(length = 256)
	private String url;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Bookmark)) {
			return false;
		}
		final Bookmark other = (Bookmark) obj;
		if (this.url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!this.url.equals(other.url)) {
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

	public String getText() {
		return this.text;
	}

	public String getUrl() {
		return this.url;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((this.url == null) ? 0 : this.url.hashCode());
		result = (prime * result) + ((this.user == null) ? 0 : this.user.hashCode());
		return result;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setUser(final User user) {
		this.user = user;
	}
}
