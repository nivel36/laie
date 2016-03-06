package ged.ejb.core.i18n;

import java.util.List;

import javax.ejb.Local;

@Local
public interface I18nDao {

	public List<I18nString> findAll();

}
