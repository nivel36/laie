package ged.ejb.export.dto;

import java.util.List;
import java.util.Objects;

public class ExportSaveDefinitionInputBean {

	private final String exportName;
	
	private final List<SaveDefinitionItem> list;
	
	public ExportSaveDefinitionInputBean(final String exportName, final List<SaveDefinitionItem> list) {
		super();
		Objects.requireNonNull(exportName);
		Objects.requireNonNull(list);
		this.exportName = exportName;
		this.list = list;
	}

	public String getExportName() {
		return exportName;
	}
	
	public List<SaveDefinitionItem> getList() {
		return list;
	}
	
	public static class SaveDefinitionItem {

		private final long idField;
		
		private final int sortOrder;

		public SaveDefinitionItem(final long idField, final int sortOrder) {
			super();
			this.idField = idField;
			this.sortOrder = sortOrder;
		}

		public long getIdField() {
			return idField;
		}

		public int getSortOrder() {
			return sortOrder;
		}
	}
}
