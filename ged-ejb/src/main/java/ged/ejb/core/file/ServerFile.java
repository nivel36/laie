package ged.ejb.core.file;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractEntity;

@Entity
public class ServerFile extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "candidateId", nullable = false)
	private Candidate candidate;

	private LocalDate date;

	private String description;

	private boolean lopd;

	@NotNull
	@Column(nullable = false)
	private String name;

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
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ServerFile other = (ServerFile) obj;
		return Objects.equals(this.uuid, other.uuid);
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public LocalDate getDate() {
		return this.date;
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

	public boolean isLopd() {
		return this.lopd;
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setLopd(final boolean lopd) {
		this.lopd = lopd;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}
}