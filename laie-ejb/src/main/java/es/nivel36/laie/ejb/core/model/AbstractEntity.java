package es.nivel36.laie.ejb.core.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity implements Identifiable, Serializable {

	private static final long serialVersionUID = 5132586442369497913L;

	@Id
	@GeneratedValue
	protected long id;

	@Version
	protected long version;

	public boolean isNew() {
		return this.id == 0;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public long getVersion() {
		return this.version;
	}

	@Override
	public void setId(final long id) {
		this.id = id;
	}

	public void setVersion(final long version) {
		this.version = version;
	}

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
		return other.id == this.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.version);
	}

	@Override
	public String toString() {
		return "AbstractEntity [id=" + id + ", version=" + version + "]";
	}
}