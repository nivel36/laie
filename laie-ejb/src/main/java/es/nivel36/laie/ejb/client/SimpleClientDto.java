package es.nivel36.laie.ejb.client;

import java.io.Serializable;
import java.util.Objects;

public class SimpleClientDto implements Serializable {
	
	private static final long serialVersionUID = 3387961039080148037L;

	private String uid;
	
	private String name;
	
	private String cif;
	
	public final String getUid() {
		return uid;
	}

	public final void setUid(String uid) {
		this.uid = uid;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getCif() {
		return cif;
	}

	public final void setCif(String cif) {
		this.cif = cif;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleClientDto other = (SimpleClientDto) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return name;
	}
}
