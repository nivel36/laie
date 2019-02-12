package ged.ejb.export.service;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.export.acquirer.ExportAcquirerUtil;
import ged.ejb.export.acquirer.ExportData;
import ged.ejb.export.dto.ExportFieldsOutputBean;
import ged.ejb.user.User;

@Stateless
public class ExportService {

	private static final String EXPORT_USERS_NAME = "USERS";
	
	@Inject
	private transient ExportDefinitionService exportDefinitionService;
	
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
