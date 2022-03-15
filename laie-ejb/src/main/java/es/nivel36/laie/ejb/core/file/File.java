package es.nivel36.laie.ejb.core.file;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.ejb.core.model.AbstractObfuscableEntity;
import es.nivel36.laie.ejb.core.model.Indexable;

@Entity
public class File extends AbstractObfuscableEntity implements Indexable {

	private static final long serialVersionUID = -2983690237456593632L;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime created;
	
	private String description;

	@NotNull
	@Column(nullable = false)
	private String name;

	@ManyToOne(cascade = { CascadeType.PERSIST}, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "physicalFileId")
	private PhysicalFile physicalFile;

	private boolean publicAccess;

	private String uid;

	public LocalDateTime getCreated() {
		return this.created;
	}

	public String getDescription() {
		return this.description;
	}

	public String getName() {
		return this.name;
	}

	public PhysicalFile getPhysicalFile() {
		return this.physicalFile;
	}

	public String getUid() {
		return uid;
	}

	public boolean isPublicAccess() {
		return this.publicAccess;
	}

	public void setCreated(final LocalDateTime created) {
		this.created = created;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPhysicalFile(final PhysicalFile physicalFile) {
		this.physicalFile = physicalFile;
	}

	public void setPublicAccess(final boolean publicAccess) {
		this.publicAccess = publicAccess;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		File other = (File) obj;
		return Objects.equals(created, other.created) && Objects.equals(name, other.name)
				&& publicAccess == other.publicAccess;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(created, name, publicAccess);
	}

	@Override
	public String toString() {
		return name;
	}
}