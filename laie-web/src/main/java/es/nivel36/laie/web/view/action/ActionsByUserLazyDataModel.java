package es.nivel36.laie.web.view.action;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.core.action.Action;
import es.nivel36.laie.ejb.core.action.ActionService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.user.User;
import jakarta.inject.Inject;

public class ActionsByUserLazyDataModel extends LazyDataModel<Action> {

	private static final long serialVersionUID = 6835795236994934142L;

	private transient @Inject ActionService actionService;

	private User user;

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		if (user == null) {
			return (int) actionService.countAll();
		}
		return (int) actionService.countAllByUser(this.user);
	}

	@Override
	public List<Action> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		if (user == null) {
			return actionService.findAll(Page.of(first, pageSize));
		}
		return actionService.findAllByUser(this.user, Page.of(first, pageSize));
	}

	public void setUser(final User user) {
		Objects.requireNonNull(user);
		this.user = user;
	}
}
