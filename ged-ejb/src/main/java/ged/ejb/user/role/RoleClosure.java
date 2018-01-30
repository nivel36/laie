package ged.ejb.user.role;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ged.ejb.core.model.AbstractEntity;

@Entity
public class RoleClosure extends AbstractEntity {

	private static final long serialVersionUID = 1887763207659077404L;

	@ManyToOne
	@JoinColumn(name = "antecessor_id", nullable = false)
	private Role antecessor;

	@ManyToOne
	@JoinColumn(name = "descendant_id", nullable = false)
	private Role descendant;

	private int pathLength;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final RoleClosure other = (RoleClosure) obj;
		return Objects.equals(other.antecessor, this.antecessor) && Objects.equals(other.descendant, this.descendant)
				&& Objects.equals(other.pathLength, this.pathLength);
	}

	public Role getAntecessor() {
		return this.antecessor;
	}

	public Role getDescendant() {
		return this.descendant;
	}

	public int getPathLength() {
		return this.pathLength;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.antecessor, this.descendant, this.pathLength);
	}

	public void setAntecessor(final Role antecessor) {
		this.antecessor = antecessor;
	}

	public void setDescendant(final Role descendant) {
		this.descendant = descendant;
	}

	public void setPathLength(final int pathLength) {
		this.pathLength = pathLength;
	}
}
