package es.nivel36.laie.department;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;

import es.nivel36.laie.core.Dto;
import es.nivel36.laie.user.SimpleUserDto;

/**
 * A department is a unit in which different users of the company that have a
 * common work or objective are grouped together.
 * 
 * @author Abel Ferrer
 *
 */
public class DepartmentDto implements Dto {

	private static final long serialVersionUID = -4297745290105100281L;

	////////////////////////////////////////////////////////////////////////////
	// VARIABLES
	////////////////////////////////////////////////////////////////////////////

	private SimpleUserDto head;

	@NotNull
	private String name;

	@NotNull
	private String uid;

	private Set<SimpleUserDto> workers;

	////////////////////////////////////////////////////////////////////////////
	// PUBLIC
	////////////////////////////////////////////////////////////////////////////

	/**
	 * {@inheritDoc}
	 */
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
		final DepartmentDto other = (DepartmentDto) obj;
		return Objects.equals(this.name, other.name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + Objects.hash(this.name);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.name;
	}

	////////////////////////////////////////////////////////////////////////////
	// GETTERS
	////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the head of department. This manager does not necessarily have to be
	 * responsible for all users in the department.
	 * 
	 * @return <tt>SimpleUserDto</tt> with the head of department or <tt>null</tt>
	 *         if department has no head.
	 */
	public SimpleUserDto getHead() {
		return this.head;
	}

	/**
	 * Returns the name of the department. This value must be unique and cannot be
	 * null.
	 * 
	 * @return <tt>String</tt> with the name of department.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUid() {
		return this.uid;
	}

	/**
	 * Returns the set of users that are part of the department. This set cannot be
	 * modified.
	 * 
	 * @return <tt>Set</tt> with the users of the department.
	 */
	public Set<SimpleUserDto> getWorkers() {
		return this.workers;
	}

	////////////////////////////////////////////////////////////////////////////
	// SETTERS
	////////////////////////////////////////////////////////////////////////////

	/**
	 * Set the head of department. This manager does not necessarily have to be
	 * responsible for all users in the department.
	 * 
	 * @param user <tt>User</tt> with the head of department or <tt>null</tt> if
	 *             department has no head.
	 */
	public void setHead(final SimpleUserDto head) {
		this.head = head;
	}

	/**
	 * Sets the name of the department. This value must be unique and cannot be
	 * <tt>null</tt>.
	 * 
	 * @param name <tt>String</tt> with the name of department.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	void setUid(final String uid) {
		this.uid = uid;
	}

	void setWorkers(final Set<SimpleUserDto> workers) {
		if (workers != null) {
			this.workers = Collections.unmodifiableSet(workers);
		} else {
			this.workers = new HashSet<>();
		}
	}
}
