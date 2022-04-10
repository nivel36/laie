package es.nivel36.laie.ejb.export.service;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.nivel36.laie.ejb.export.acquirer.ExportAcquirerUtil;
import es.nivel36.laie.ejb.export.acquirer.ExportData;
import es.nivel36.laie.ejb.export.dto.ExportFieldsOutputBean;
import es.nivel36.laie.ejb.user.User;

@Stateless
public class ExportService {

	private static final String EXPORT_USERS_NAME = "USERS";
	
	@Inject
	private ExportDefinitionService exportDefinitionService;
	
	public ExportData getExportUsersData(final List<User> users) {
		Objects.requireNonNull(users);
		final ExportFieldsOutputBean definition = getExportDefinitionService().findDefinitionByExport(EXPORT_USERS_NAME);
		Objects.requireNonNull(definition);
		Objects.requireNonNull(definition.getList()); // TODO ivmedina revisar
		return ExportAcquirerUtil.getExportData(users, definition, User.class);
	}
	
	public ExportDefinitionService getExportDefinitionService() {
		return exportDefinitionService;
	}

	public void setExportDefinitionService(ExportDefinitionService exportDefinitionService) {
		this.exportDefinitionService = exportDefinitionService;
	}
}
