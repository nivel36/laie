package ged.ejb.export.dto;

import java.util.List;
import java.util.Objects;

public class ExportSaveDefinitionInputBean {

	private final List<SaveDefinitionItem> list;
	
	public ExportSaveDefinitionInputBean(final List<SaveDefinitionItem> list) {
		super();
		Objects.requireNonNull(list);
		this.list = list;
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
