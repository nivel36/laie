package ged.ejb.core.i18n.impl;

import java.util.List;

import javax.inject.Inject;

import ged.ejb.core.i18n.I18nDao;
import ged.ejb.core.i18n.I18nString;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.model.Repository;

@Repository
public class I18nDaoImpl implements I18nDao {

	@Inject
	@Repository
	private PersistenceFacade persistenceFacade;

	@Override
	public List<I18nString> findAll() {
		return this.persistenceFacade.getAll(I18nString.class);
	}

}
