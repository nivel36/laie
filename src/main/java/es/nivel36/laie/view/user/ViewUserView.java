package es.nivel36.laie.view.user;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

import es.nivel36.laie.core.view.AbstractView;
import es.nivel36.laie.user.UserDto;

@Named
@ViewScoped
public class ViewUserView extends AbstractView {

	private static final long serialVersionUID = -7976662872898546674L;

	@Inject
	@Param(name = "uid", required = true, converter = "userConverter")
	protected UserDto user;

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
}
