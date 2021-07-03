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
package es.nivel36.laie.user;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.core.service.AbstractEntity;

@Entity
public class UserClosure extends AbstractEntity {

	private static final long serialVersionUID = -7895128921806782240L;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "antecessor_id", nullable = false)
	private User antecessor;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "descendant_id", nullable = false)
	private User descendant;

	@NotNull
	@Column(nullable = false)
	private int pathLength;

	public UserClosure() {

	}

	public UserClosure(final User antecessor, final User descendant, final Integer pathLength) {
		this.antecessor = antecessor;
		this.descendant = descendant;
		this.pathLength = pathLength;
	}

	///////////////////////////////////////////////////////////////////////////
	// PUBLIC
	///////////////////////////////////////////////////////////////////////////

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final UserClosure other = (UserClosure) obj;
		return Objects.equals(other.antecessor, this.antecessor) && Objects.equals(other.descendant, this.descendant)
				&& Objects.equals(other.pathLength, this.pathLength);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.antecessor, this.descendant, this.pathLength);
	}

	///////////////////////////////////////////////////////////////////////////
	// GETTER
	///////////////////////////////////////////////////////////////////////////

	public User getAntecessor() {
		return this.antecessor;
	}

	public User getDescendant() {
		return this.descendant;
	}

	public int getPathLength() {
		return this.pathLength;
	}

	///////////////////////////////////////////////////////////////////////////
	// SETTER
	///////////////////////////////////////////////////////////////////////////

	public void setAntecessor(final User antecessor) {
		this.antecessor = antecessor;
	}

	public void setDescendant(final User descendant) {
		this.descendant = descendant;
	}

	public void setPathLength(final int pathLength) {
		this.pathLength = pathLength;
	}
}