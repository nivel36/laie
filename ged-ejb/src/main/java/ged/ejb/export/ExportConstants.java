package ged.ejb.export;

public interface ExportConstants {

	interface FindExportByExportName {
		String QUERY_NAME = "Export.findByExportName";
		interface Params {
			String EXPORT_NAME = "exportName";
		}
	}
	
	interface FindFieldsByExport {
		String QUERY_NAME = "ExportField.findAllByExport";
		interface Params {
			String EXPORT = "export";
		}
	}
}
