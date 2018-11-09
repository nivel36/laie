package ged.web.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.Origin;
import ged.ejb.user.UserService;

@Named
@ApplicationScoped
public class Origins implements Serializable {

	private static final long serialVersionUID = -2279622649333101152L;

	private List<Origin> listOfOrigins;

	@Inject
	private transient UserService userService;

	public List<Origin> getList() {
		return listOfOrigins;
	}

	@PostConstruct
	public void init() {
		listOfOrigins = userService.findAllOrigins();
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

}