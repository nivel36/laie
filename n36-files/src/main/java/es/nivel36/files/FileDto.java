package es.nivel36.files;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class FileDto implements Serializable {

	private static final long serialVersionUID = -1408778960275004768L;

	private LocalDateTime created;

	private String description;

	private String name;

	private String path;

	private boolean publicAccess;

	private String uid;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileDto other = (FileDto) obj;
		return Objects.equals(created, other.created) && Objects.equals(name, other.name)
				&& publicAccess == other.publicAccess;
	}

	public LocalDateTime getCreated() {
		return this.created;
	}

	public String getDescription() {
		return this.description;
	}

	public String getName() {
		return this.name;
	}

	public String getPath() {
		return path;
	}

	public String getUid() {
		return uid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(created, name, publicAccess);
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

	public void setPath(String path) {
		this.path = path;
	}

	public void setPublicAccess(final boolean publicAccess) {
		this.publicAccess = publicAccess;
	}

	void setUid(String uid) {
		this.uid = uid;
	}
}
