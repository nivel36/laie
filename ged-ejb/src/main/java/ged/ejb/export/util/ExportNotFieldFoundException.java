package ged.ejb.export.util;

import java.util.Objects;

public class ExportNotFieldFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String exportName;

	public ExportNotFieldFoundException(final String exportName) {
		super();
		Objects.requireNonNull(exportName);
		this.exportName = exportName;
	}

	public String getExportName() {
		return exportName;
	}
}
