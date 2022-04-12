package es.nivel36.laie.ejb.core.bookmark;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.user.User;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "url", "userId" }))
public class 
 extends AbstractEntity {

	private static final long serialVersionUID = -2180672310644250195L;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String url;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return 31*Objects.hash(url, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		final Bookmark other = (Bookmark) obj;
		return Objects.equals(url, other.url) && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return title;
	}
}