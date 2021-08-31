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

import java.time.LocalDateTime;
import java.util.Objects;

import es.nivel36.laie.core.Dto;

/**
 * *
 * <p>
 * A <tt>FileDto<tt> is a representation of a file uploaded to the file
 * system.<br/>
 * A file can be uploaded multiple times, the uploads will be differentiated by
 * the date of creation and its (unique) identifier.
 * </p>
 * 
 * @see File
 * @see PhysicalFile
 *
 * @author Abel Ferrer
 * 
 */
public class FileDto implements Dto {

	private static final long serialVersionUID = -310795968243464178L;

	private LocalDateTime created;

	private String description;

	private String name;

	private String path;
	
	private String uid;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileDto other = (FileDto) obj;
		return uid == other.uid;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public String getUid() {
		return uid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(uid);
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return name;
	}
}
