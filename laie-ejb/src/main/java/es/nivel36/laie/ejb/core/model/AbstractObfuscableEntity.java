package es.nivel36.laie.ejb.core.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AbstractObfuscableEntity extends AbstractEntity implements Obfuscable {

	private static final long serialVersionUID = -2683028217207608647L;

	@NotNull
	@Column(unique = true, nullable = false)
	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractObfuscableEntity other = (AbstractObfuscableEntity) obj;
		return Objects.equals(uid, other.uid);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uid);
	}

	@Override
	public String toString() {
		return uid;
	}
}
