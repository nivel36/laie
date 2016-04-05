package ged.web.view;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.model.Action;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.user.UserService;
import ged.web.core.view.SessionBean;

@Named
@RequestScoped
public class ActionsBean implements Serializable {

	private static final long serialVersionUID = 6101883862412908337L;

	@Inject
	protected FacesContext facesContext;

	@Inject
	protected Logger logger;

	@Inject
	protected SessionBean sessionBean;

	@Inject
	private UserService userService;

	public void add(final AuditedEntity entity) {
		final Action action = createAction(entity);
		if (this.sessionBean.containsAction(action)) {
			this.sessionBean.addAction(action);
		} else {
			this.sessionBean.addAction(action);
			this.userService.insertAction(action);
		}
	}

	private Action createAction(final AuditedEntity entity) {
		final Action action = new Action();
		final String url = getUrl(entity);
		action.setUrl(url);
		action.setText(entity.toString());
		action.setUser(this.sessionBean.getUser());
		return action;
	}

	private String getUrl(final AuditedEntity entity) {
		final String contextPath = this.facesContext.getExternalContext().getRequestContextPath();
		String viewId = this.facesContext.getViewRoot().getViewId();
		viewId = viewId.replace("Edit", "View");
		final String url = contextPath + viewId + "?id=" + entity.getId();
		return url;
	}
}
