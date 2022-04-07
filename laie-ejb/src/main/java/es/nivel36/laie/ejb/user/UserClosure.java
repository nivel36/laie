package es.nivel36.laie.ejb.user;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import es.nivel36.laie.ejb.core.model.AbstractEntity;

@Entity
public class UserClosure extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "antecessor_id", nullable = false)
	private User antecessor;

	@ManyToOne
	@JoinColumn(name = "descendant_id", nullable = false)
	private User descendant;

	@Column(nullable = false)
	private Integer pathLength;

	public UserClosure() {

	}

	public UserClosure(final User antecessor, final User descendant, final Integer pathLength) {
		this.antecessor = antecessor;
		this.descendant = descendant;
		this.pathLength = pathLength;
	}

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

	public User getAntecessor() {
		return this.antecessor;
	}

	public User getDescendant() {
		return this.descendant;
	}

	public Integer getPathLength() {
		return this.pathLength;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.antecessor, this.descendant, this.pathLength);
	}

	public void setAntecessor(final User antecessor) {
		this.antecessor = antecessor;
	}

	public void setDescendant(final User descendant) {
		this.descendant = descendant;
	}

	public void setPathLength(final Integer pathLength) {
		this.pathLength = pathLength;
	}
}