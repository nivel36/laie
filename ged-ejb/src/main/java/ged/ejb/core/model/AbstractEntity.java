package ged.ejb.core.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity implements Identificable, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	protected long id;

	@Version
	protected long version;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractEntity other = (AbstractEntity) obj;
		return other.id == this.id;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public long getVersion() {
		return this.version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (this.id ^ this.id >>> 32);
		return result;
	}

	@Override
	public void setId(final long id) {
		this.id = id;
	}

	public void setVersion(final long version) {
		this.version = version;
	}
}