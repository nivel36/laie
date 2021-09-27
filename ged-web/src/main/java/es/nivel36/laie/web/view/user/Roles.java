package es.nivel36.laie.web.view.user;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import es.nivel36.laie.ejb.user.Role;

@Named
@ApplicationScoped
public class Roles implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Role> listOfRoles;

	public List<Role> getList() {
		return this.listOfRoles;
	}

	@PostConstruct
	public void init() {
		this.listOfRoles = Arrays.asList(Role.values());
	}
}
