package ged.ejb.export.service;

import ged.ejb.core.Service;
import ged.ejb.export.dto.ExportFieldsOutputBean;
import ged.ejb.export.dto.ExportSaveDefinitionInputBean;
import ged.ejb.export.entity.Export;

public interface ExportService extends Service<Export> {

	ExportFieldsOutputBean findFieldsByExport(String exportName);
	
	ExportFieldsOutputBean findDefinitionByExport(String exportName);
	
	void saveDefinition(String exportName, ExportSaveDefinitionInputBean target);
}
