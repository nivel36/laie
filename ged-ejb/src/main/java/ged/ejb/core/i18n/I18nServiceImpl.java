package ged.ejb.core.i18n;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.model.Repository;

@Stateless
public class I18nServiceImpl implements I18nService {

	@Inject
	@Repository
	private PersistenceFacade persistenceFacade;

	@Override
	public List<I18nString> findAll() {
		return this.persistenceFacade.findAll(I18nString.class);
	}
}
