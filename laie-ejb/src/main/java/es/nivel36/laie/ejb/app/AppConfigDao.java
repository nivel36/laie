package es.nivel36.laie.ejb.app;

import es.nivel36.laie.ejb.core.model.AbstractDao;

public class AppConfigDao extends AbstractDao {
	
	public AppConfig findAppConfig() {
		return this.find(AppConfig.class, 1L);
	}
}