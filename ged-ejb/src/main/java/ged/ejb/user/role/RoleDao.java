package ged.ejb.user.role;

import static ged.ejb.core.util.Parameters.map;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class RoleDao extends AbstractDao<Role> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	public Role findAdmin() {
		return this.findByQuery(Role.class, "Role.findAdmin", null);
	}

	public Role findRoleByName(final String roleName) {
		Objects.requireNonNull(roleName);
		return this.findByQuery(Role.class, "Role.findRoleByName", map("name", roleName));
	}

	public List<Role> findSubordinateRoles(final Role role) {
		Objects.requireNonNull(role);
		try {
			return this.findByQuery(Role.class, "Role.findSubordinateRoles", map("antecessor", role), 0, 0);
		}
		catch (final NoResultException e) {
			logger.debug("No subordinate roles for role {}", role.getName(), e);
			return new ArrayList<>();
		}
	}

	@Override
	protected Class<Role> getType() {
		return Role.class;
	}

	public List<Role> search(final String searchText) {
		throw new UnsupportedOperationException();
	}
}
