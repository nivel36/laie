package es.nivel36.laie.web.view.action;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.core.action.Action;
import es.nivel36.laie.ejb.core.action.ActionService;
import es.nivel36.laie.ejb.core.model.Page;

public class ActionsLazyDataModel extends LazyDataModel<Action> {

	private static final long serialVersionUID = 7176885377018794354L;
	
	@Inject
	private transient ActionService actionService;

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return (int) actionService.countAll();
	}

	@Override
	public List<Action> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		return actionService.findAll(Page.of(first, pageSize));
	}
}