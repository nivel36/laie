package ged.ejb.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ged.ejb.core.model.AbstractEntity;

@Entity
public class Role extends AbstractEntity {

	private static final long serialVersionUID = 5722113796215191203L;

	private String name;

	@ManyToOne
	@JoinColumn(name = "roleManagerId", nullable = true)
	private Role roleManager;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "roleManager", orphanRemoval = false)
	private List<Role> roleTeam;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}
