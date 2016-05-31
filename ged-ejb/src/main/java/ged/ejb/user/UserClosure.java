package ged.ejb.user;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ged.ejb.core.model.AbstractEntity;

@Entity
public class UserClosure extends AbstractEntity {

	private static final long serialVersionUID = -4505908505220258870L;

	@ManyToOne
	@JoinColumn(name = "antecessor_id", nullable = false)
	private User antecessor;

	@ManyToOne
	@JoinColumn(name = "descendant_id", nullable = false)
	private User descendant;

	private int pathLength;

	public User getAntecessor() {
		return this.antecessor;
	}

	public User getDescendant() {
		return this.descendant;
	}

	public int getPathLength() {
		return this.pathLength;
	}

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
