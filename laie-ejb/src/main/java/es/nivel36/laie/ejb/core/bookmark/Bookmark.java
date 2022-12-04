package es.nivel36.laie.ejb.core.bookmark;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(indexes = { @javax.persistence.Index(name = "UX_BOOKMARK_URL", columnList = "url", unique = true) })
public class Bookmark extends AbstractEntity {

	private static final long serialVersionUID = -2180672310644250195L;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false, unique = true)
	private String url;

	@ManyToMany
	private Set<User> users = new HashSet<>();

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUser(final Set<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		return 31*Objects.hash(url);
	}

	@Override
	public boolean equals(final Object obj) {
		if(obj == null) {
			return false;
		}
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		final Bookmark other = (Bookmark) obj;
		return Objects.equals(url, other.url);
	}

	@Override
	public String toString() {
		return title;
	}
}