package ged.ejb.export.dto;

import java.util.List;
import java.util.Objects;

public class ExportFieldsOutputBean {

	private final long idExport;

	private final List<ExportFieldItem> list;

	public ExportFieldsOutputBean(final long idExport, final List<ExportFieldItem> list) {
		super();
		Objects.requireNonNull(list);
		this.idExport = idExport;
		this.list = list;
	}

	public long getIdExport() {
		return idExport;
	}

	public List<ExportFieldItem> getList() {
		return list;
	}

	public static final class ExportFieldItem {

		private final long idField;

		private final String literalId;
		
		private final String acquiredClass;

		public ExportFieldItem(final long idField, final String literalId, final String acquiredClass) {
			super();
			Objects.requireNonNull(literalId);
			Objects.requireNonNull(acquiredClass);
			this.idField = idField;
			this.literalId = literalId;
			this.acquiredClass = acquiredClass;
		}

		public long getIdField() {
			return idField;
		}

		public String getLiteralId() {
			return literalId;
		}

		public String getAcquiredClass() {
			return acquiredClass;
		}
	}
}
