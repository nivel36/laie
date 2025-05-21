package es.nivel36.laie.ejb.core.file;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import es.nivel36.laie.ejb.candidate.CandidateFileAttachment;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class PhysicalFile extends AbstractEntity {

	private static final long serialVersionUID = -5784130825846473478L;

	@NotBlank
	@Column(nullable = false)
	private String absolutePath;

	@NotBlank
	@Column(nullable = false)
	private String bucket;

	@NotBlank
	@Column(nullable = false, unique = true, updatable = false)
	private String contentHash;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime created;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<CandidateFileAttachment> files;

	@NotBlank
	@Column(nullable = false)
	private String relativePath;

	@NotBlank
	@Column(nullable = false, unique = true, updatable = false)
	private String uId;

	public String getAbsolutePath() {
		return this.absolutePath;
	}

	public String getBucket() {
		return bucket;
	}

	public String getContentHash() {
		return this.contentHash;
	}

	public LocalDateTime getCreated() {
		return this.created;
	}

	public Set<CandidateFileAttachment> getFiles() {
		return files;
	}

	public String getRelativePath() {
		return this.relativePath;
	}

	public String getPath() {
		return this.relativePath;
	}

	public String getUId() {
		return this.uId;
	}

	public void setAbsolutePath(final Path absolutePath) {
		this.absolutePath = absolutePath.toString();
	}

	public void setAbsolutePath(final String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public void setBucket(final String bucket) {
		this.bucket = bucket;
	}

	public void setContentHash(final String contentHash) {
		this.contentHash = contentHash;
	}

	public void setCreated(final LocalDateTime created) {
		this.created = created;
	}

	public void setFiles(final Set<CandidateFileAttachment> files) {
		this.files = files;
	}

	public void setRelativePath(final Path relativePath) {
		this.relativePath = relativePath.toString();
	}

	public void setRelativePath(final String relativePath) {
		this.relativePath = relativePath;
	}

	public void setUId(final String uId) {
		this.uId = uId;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || this.getClass() != obj.getClass()) {
			return false;
		}
		final PhysicalFile other = (PhysicalFile) obj;
		return Objects.equals(this.uId, other.uId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.uId);
	}

	@Override
	public String toString() {
		return uId;
	}
}