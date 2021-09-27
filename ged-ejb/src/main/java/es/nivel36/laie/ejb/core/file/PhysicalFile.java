package es.nivel36.laie.ejb.core.file;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.ejb.core.model.AbstractEntity;

@Entity
public class PhysicalFile extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(nullable = false)
	private String absolutePath;

	@NotNull
	@Column(nullable = false, unique = true, updatable = false)
	private String contentHash;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime created;

	@NotNull
	@Column(nullable = false)
	private String relativePath;

	@NotNull
	@Column(nullable = false, unique = true, updatable = false)
	private String uuid;

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final PhysicalFile other = (PhysicalFile) obj;
		return Objects.equals(this.uuid, other.uuid);
	}

	public String getAbsolutePath() {
		return this.absolutePath;
	}

	public String getContentHash() {
		return this.contentHash;
	}

	public LocalDateTime getCreated() {
		return this.created;
	}

	public String getRelativePath() {
		return this.relativePath;
	}

	public String getUuid() {
		return this.uuid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.uuid);
	}

	public void setAbsolutePath(final Path absolutePath) {
		this.absolutePath = absolutePath.toString();
	}

	public void setAbsolutePath(final String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public void setContentHash(final String contentHash) {
		this.contentHash = contentHash;
	}

	public void setCreated(final LocalDateTime created) {
		this.created = created;
	}

	public void setRelativePath(final Path relativePath) {
		this.relativePath = relativePath.toString();
	}

	public void setRelativePath(final String relativePath) {
		this.relativePath = relativePath;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}
}
