package es.nivel36.laie.service.user;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.service.AbstractEntity;

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