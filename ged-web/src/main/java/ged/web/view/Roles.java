package ged.web.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;

@Named
@ApplicationScoped
public class Roles implements Serializable {

	private static final long serialVersionUID = 6616698634859334823L;

	private List<Role> listOfRoles;

	@Inject
	private transient UserService userService;

	public List<Role> getList() {
		return listOfRoles;
	}

	@PostConstruct
	public void init() {
		listOfRoles = userService.findAllRoles();
	}
}
