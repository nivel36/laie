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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.core.service.AbstractEntity;

/**
 * <p>
 * A physical file is a database representation of a file in the file
 * system.<br/>
 * A physical file cannot be duplicated.<br/>
 * Its relationship to a {@link es.nivel36.laie.file.File File}. is
 * many-to-one; a physical file may be associated with multiple files.<br/>
 * When the file on the file system is deleted, its record in the database must
 * also be deleted.
 * </p>
 * 
 * @see File
 * @author Abel Ferrer
 */
@Entity
public class PhysicalFile extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public PhysicalFile() {
	}

	public PhysicalFile(final String uuid, final Path path, final String hash) {
		this.uuid = uuid;
		this.contentHash = hash;
		this.path = path.toString();
		this.created = LocalDateTime.now();
	}

	@NotNull
	@Column(nullable = false)
	private String path;

	@NotNull
	@Column(nullable = false, unique = true, updatable = false)
	private String contentHash;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime created;

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
		return this.path;
	}

	public String getContentHash() {
		return this.contentHash;
	}

	public LocalDateTime getCreated() {
		return this.created;
	}

	public String getUuid() {
		return this.uuid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.uuid);
	}

	public void setPath(final Path path) {
		this.path = path.toString();
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public void setContentHash(final String contentHash) {
		this.contentHash = contentHash;
	}

	public void setCreated(final LocalDateTime created) {
		this.created = created;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}
}
