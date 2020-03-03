package ged.ejb.core.file;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractEntity;

@Entity
public class File extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "candidateId", nullable = false)
	private Candidate candidate;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime created;

	private String description;

	@NotNull
	@Column(nullable = false)
	private String name;

	@NotNull
	@Column(nullable = false)
	private String uuid;

	public File() {
	}

	public File(final String name) {
		Objects.requireNonNull(name);
		this.name = name;
		this.created = LocalDateTime.now();
		this.uuid = UUID.randomUUID().toString();
	}

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
		final File other = (File) obj;
		return Objects.equals(this.uuid, other.uuid);
	}

	public Candidate getCandidate() {
		return this.candidate;
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

	public String getUuid() {
		return this.uuid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.uuid);
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
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

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}
}