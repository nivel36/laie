package ged.web.view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.action.Action;
import ged.ejb.core.action.ActionService;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class ActionBean extends AbstractPageBean {

	private static final long serialVersionUID = 6101883862412908337L;

	private List<ActionDto> actions;

	@Inject
	private transient ActionService actionService;

	public List<ActionDto> getActions() {
		return this.actions;
	}

	private String getUrl(final Action action) {
		final String contextPath = this.externalContext.getRequestContextPath();
		final String className = action.getEntityClass().toLowerCase();
		final StringBuilder url = new StringBuilder();
		url.append(contextPath).append("/faces/").append(className).append("/").append(className).append("View.xhtml")
				.append("?id=").append(action.getEntityId());
		return url.toString();
	}

	@PostConstruct
	public void init() {
		final List<Action> entityActions = this.actionService.findAllByUser(this.sessionBean.getUser());
		this.actions = new ArrayList<ActionDto>();
		for (final Action action : entityActions) {
			final ActionDto dto = new ActionDto();
			dto.setText(action.toString());
			final String url = getUrl(action);
			dto.setUrl(url);
			this.actions.add(dto);
		}
	}

	public void setActions(final List<ActionDto> actions) {
		this.actions = actions;
	}

	public void setActionService(final ActionService actionService) {
		this.actionService = actionService;
	}
}