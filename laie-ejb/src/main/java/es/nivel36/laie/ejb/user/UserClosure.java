package es.nivel36.laie.ejb.user;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "PERSON_CLOSURE", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_PERSON_CLOSURE_ANTECESSOR_ID_DESCENDANT_ID_PATH_LENGTH", columnNames = {
				"ANTECESSOR_ID", "DESCENDANT_ID", "PATH_LENGTH" }) }, //
		indexes = {
				@Index(name = "UX_PERSON_CLOSURE_ANTECESSOR_ID_DESCENDANT_ID_PATH_LENGTH", columnList = "ANTECESSOR_ID, DESCENDANT_ID, PATH_LENGTH", unique = true) })
public class UserClosure extends AbstractEntity {

	private static final long serialVersionUID = 6018390713882649369L;

	@ManyToOne
	@JoinColumn(name = "ANTECESSOR_ID", nullable = false)
	private User antecessor;

	@ManyToOne
	@JoinColumn(name = "DESCENDANT_ID", nullable = false)
	private User descendant;

	@Column(name = "PATH_LENGTH", nullable = false)
	private Integer pathLength;

	public UserClosure() {

	}

	public UserClosure(final User antecessor, final User descendant, final Integer pathLength) {
		this.antecessor = antecessor;
		this.descendant = descendant;
		this.pathLength = pathLength;
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

	public void setAntecessor(final User antecessor) {
		this.antecessor = antecessor;
	}

	public void setDescendant(final User descendant) {
		this.descendant = descendant;
	}

	public void setPathLength(final Integer pathLength) {
		this.pathLength = pathLength;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.antecessor, this.descendant, this.pathLength);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final UserClosure other = (UserClosure) obj;
		return Objects.equals(other.antecessor, this.antecessor) && Objects.equals(other.descendant, this.descendant)
				&& Objects.equals(other.pathLength, this.pathLength);
	}

	@Override
	public String toString() {
		return "UserClosure [antecessor=" + antecessor + ", descendant=" + descendant + ", pathLength=" + pathLength
				+ "]";
	}
}