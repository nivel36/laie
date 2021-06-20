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
package es.nivel36.laie.service;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@Version
	private long version;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final AbstractEntity other = (AbstractEntity) obj;
		return (this.id == other.id) && (this.version == other.version);
	}

	public long getId() {
		return this.id;
	}

	public long getVersion() {
		return this.version;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.version);
	}

	public void setId(final long id) {
		this.id = id;
	}

	public void setVersion(final long version) {
		this.version = version;
	}
}
