package ged.ejb.core.i18n.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.i18n.I18nDao;
import ged.ejb.core.i18n.I18nService;
import ged.ejb.core.i18n.I18nString;
import ged.ejb.core.model.Repository;

@Stateless
public class I18nServiceImpl implements I18nService {

	@Inject
	@Repository
	private I18nDao i18nDao;

	@Override
	public List<I18nString> findAll() {
		return this.i18nDao.findAll();
	}
}
