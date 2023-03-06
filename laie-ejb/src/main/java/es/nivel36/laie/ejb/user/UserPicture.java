package es.nivel36.laie.ejb.user;

import java.io.Serializable;
import java.util.Objects;

import es.nivel36.laie.ejb.core.file.PhysicalFile;
import es.nivel36.laie.ejb.core.model.Identifiable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

@Entity
public class UserPicture implements Identifiable, Serializable {

	private static final long serialVersionUID = -8087629702378362720L;

	@Id
	@Column(name = "user_id")
	private long id;

	@Version
	private long version;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "PHYSICAL_FILE_ID", nullable = false, updatable = false)
	private PhysicalFile physicalFile;

	@NotNull
	@OneToOne
	@MapsId
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPath() {
		return this.getPhysicalFile().getRelativePath();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public PhysicalFile getPhysicalFile() {
		return physicalFile;
	}

	public void setPhysicalFile(PhysicalFile physicalFile) {
		this.physicalFile = physicalFile;
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, physicalFile);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPicture other = (UserPicture) obj;
		return Objects.equals(user, other.user) && Objects.equals(physicalFile, other.physicalFile);
	}
}