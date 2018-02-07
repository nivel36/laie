package ged.web.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.action.Action;
import ged.ejb.core.action.ActionService;
import ged.web.core.view.AbstractBean;

@Named
@RequestScoped
public class ActionBean extends AbstractBean {

	private static final long serialVersionUID = 6101883862412908337L;

	private List<Action> actions;

	@Inject
	private transient ActionService actionService;

	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

	private String buildUrl(final String className) {
		final StringBuilder sb = new StringBuilder();
		sb.append("/faces/").append(className).append("/").append(className).append("View.xhtml").append("?id=");
		return sb.toString();
	}

	private void findAllActions() {
		this.actions = this.actionService.findAllByUser(this.sessionBean.getUser());
	}

	public List<Action> getActions() {
		return this.actions;
	}

	private String getUrl(final Action action) {
		final String entityClass = action.getEntityClass();
		final String className = entityClass.substring(0, 1).toLowerCase() + entityClass.substring(1);
		return buildUrl(className) + action.getEntityId();
	}

	public String go(final Action action) {
		final String url = getUrl(action);
		return url + "&faces-redirect=true";
	}

	@PostConstruct
	public void init() {
		findAllActions();
	}
}