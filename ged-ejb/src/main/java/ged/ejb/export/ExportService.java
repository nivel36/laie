package ged.ejb.export;

import java.util.List;

import ged.ejb.core.Service;

public interface ExportService extends Service<ExportField> {

	List<ExportField> findFieldsByExport(String exportName);
}
