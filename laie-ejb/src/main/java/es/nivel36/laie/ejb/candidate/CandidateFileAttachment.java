package es.nivel36.laie.ejb.candidate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import es.nivel36.laie.ejb.core.file.PhysicalFile;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class CandidateFileAttachment extends AbstractEntity {

	private static final long serialVersionUID = -2983690237456593632L;

	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private Candidate candidate;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime created;

	private String description;

	@NotBlank
	@Column(nullable = false)
	private String name;

	@ManyToOne(cascade = { CascadeType.PERSIST }, optional = false)
	@JoinColumn(name = "physicalFileId", nullable = false, updatable = false)
	private PhysicalFile physicalFile;

	private boolean publicAccess;

	public Candidate getCandidate() {
		return candidate;
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
		if (physicalFile == null) {
			return null;
		}
		return physicalFile.getRelativePath();
	}

	public PhysicalFile getPhysicalFile() {
		return this.physicalFile;
	}

	public boolean isPublicAccess() {
		return this.publicAccess;
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setCreated(final LocalDateTime created) {
		this.created = created.truncatedTo(ChronoUnit.SECONDS);
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
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (getClass() != obj.getClass())) {
			return false;
		}
		final CandidateFileAttachment other = (CandidateFileAttachment) obj;
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