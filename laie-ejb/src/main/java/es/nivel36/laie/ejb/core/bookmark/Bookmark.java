package es.nivel36.laie.ejb.core.bookmark;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Entity representing a bookmark.
 * <p>
 * A bookmark is uniquely identified by its URL and has a title. This entity
 * also maintains a many-to-many relationship with users, which indicates the
 * users that have saved the bookmark.
 * </p>
 *
 * <p>
 * The URL is enforced to be unique in the database, ensuring that each bookmark
 * is distinct. The class extends {@code AbstractEntity}, inheriting common entity
 * properties.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 *     Bookmark bookmark = new Bookmark();
 *     bookmark.setTitle("Example Title");
 *     bookmark.setUrl("http://example.com");
 * </pre>
 * </p>
 *
 * @see es.nivel36.laie.ejb.core.model.AbstractEntity
 * @see es.nivel36.laie.ejb.user.User
 */
@Entity
@Table(indexes = { @Index(name = "UX_BOOKMARK_URL", columnList = "url", unique = true) })
public class Bookmark extends AbstractEntity {

    private static final long serialVersionUID = -2180672310644250195L;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String url;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PERSON_BOOKMARK",
               joinColumns = @JoinColumn(name = "PERSON_ID"),
               inverseJoinColumns = @JoinColumn(name = "BOOKMARK_ID"))
    private Set<User> users = new HashSet<>();

    /**
     * Retrieves the title of the bookmark.
     *
     * @return the title of the bookmark
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the bookmark.
     *
     * @param title the title to set; must not be null
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Retrieves the URL of the bookmark.
     *
     * @return the URL of the bookmark
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL of the bookmark.
     *
     * @param url the URL to set; must be unique and not null
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * Retrieves the set of users associated with this bookmark.
     *
     * @return a set of users who have this bookmark
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Sets the users associated with this bookmark.
     *
     * @param users the set of users to associate with this bookmark
     */
    public void setUsers(final Set<User> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(url);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bookmark other = (Bookmark) obj;
        return Objects.equals(url, other.url);
    }

    @Override
    public String toString() {
        return title;
    }
}
