package ged.web.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractBean;
import ged.web.core.view.Paginator;

@Named
@ViewScoped
public class GlobalSearchBean extends AbstractBean {

	private static final long serialVersionUID = 8268523301916849175L;

	private String text;

	private List<User> userDataList;

	private Paginator<User> userPaginator;

	@Inject
	private UserService userService;

	public String getText() {
		return this.text;
	}

	public List<User> getUserDataList() {
		return this.userDataList;
	}

	public Paginator<User> getUserPaginator() {
		return this.userPaginator;
	}

	public void search() {
		if ((this.text == null) || (this.text.length() < 3)) {
			addMessage(FacesMessage.SEVERITY_WARN, "error.search.camp_to_short", "error.search.camp_to_short");
		} else {
			this.userDataList = this.userService.fullSearch(this.text);
		}
	}

	public void setText(final String text) {
		this.text = text;
	}

	public void setUserDataList(final List<User> userDataList) {
		this.userDataList = userDataList;
	}

	public void setUserPaginator(final Paginator<User> userPaginator) {
		this.userPaginator = userPaginator;
	}
}
