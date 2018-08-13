package ged.ejb.export.util;

public interface ExportConstants {

	interface FindExportByExportName {
		String QUERY_NAME = "Export.findByExportName";
		interface Params {
			String EXPORT_NAME = "exportName";
		}
	}
}
