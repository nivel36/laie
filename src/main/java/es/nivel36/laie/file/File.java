/*
* Laie
* Copyright (C) 2021  Abel Ferrer
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.nivel36.laie.file;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.core.service.AbstractIdentifiableEntity;
import es.nivel36.laie.user.User;

/**
 * <p>
 * A file is a database representation of a file uploaded to the file
 * system.<br/>
 * A file can be uploaded multiple times, but only one copy will be stored on
 * the file system. This is associated with the
 * {@link es.nivel36.laie.file.PhysicalFile PhysicalFile}, which is the file
 * system representation of the file.
 * </p>
 *
 * @see PhysicalFile
 * @author Abel Ferrer
 *
 */
@Entity
public class File extends AbstractIdentifiableEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime created;

	private String description;

	@NotNull
	@Column(nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ownerId")
	private User owner;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "physicalFileId")
	private PhysicalFile physicalFile;

	public File() {
	}

	public File(final String name) {
		Objects.requireNonNull(name);
		this.name = name;
		this.created = LocalDateTime.now();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (this.getClass() != obj.getClass())) {
			return false;
		}
		final File other = (File) obj;
		return Objects.equals(this.created, other.created) && Objects.equals(this.name, other.name);
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

	public User getOwner() {
		return this.owner;
	}

	public Path getPath() {
		return Path.of(this.physicalFile.getAbsolutePath());
	}

	public PhysicalFile getPhysicalFile() {
		return this.physicalFile;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.created, this.name);
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

	public void setOwner(final User owner) {
		this.owner = owner;
	}

	public void setPhysicalFile(final PhysicalFile physicalFile) {
		this.physicalFile = physicalFile;
	}

	@Override
	public String toString() {
		return this.name;
	}
}