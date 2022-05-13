package es.nivel36.laie.web.core.view;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.core.action.Action;
import es.nivel36.laie.ejb.core.action.ActionService;
import es.nivel36.laie.ejb.core.model.Page;

public class UserActionsLazyDataModel extends LazyDataModel<Action>  {
	
	private static final long serialVersionUID = 6835795236994934142L;

	@Inject
	private transient ActionService actionService;
	
	@Inject
	private transient SessionUser sessionUser;
	
	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return 10;
	}

	@Override
	public List<Action> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		return actionService.findAllByUser(sessionUser.get(), Page.of(first, pageSize));
	}
}
