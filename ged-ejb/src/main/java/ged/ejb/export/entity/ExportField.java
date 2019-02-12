package ged.ejb.export.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import ged.ejb.core.model.AbstractEntity;

@Entity
public class ExportField extends AbstractEntity {

	private static final long serialVersionUID = 2230780249719376394L;

	// TODO ivmedina unique export-fieldName
	// TODO ivmedina unique export-sortOrder

	@ManyToOne
	@JoinColumn(name = "exportId", nullable = false)
	private Export export;

	@Column(length = 50, nullable = false)
	private String fieldName;

	@Column(scale = 6, precision = 0, nullable = false)
	private int sortOrder;

	@Column(length = 50, nullable = false)
	private String literalId;
	
	@Column(length = 200, nullable = false)
	private String acquirerClass;

	@Column(nullable = false)
	private boolean disabled;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "exportField")
	private ExportDefinition exportDefinition;

	public Export getExport() {
		return export;
	}

	public void setExport(Export export) {
		this.export = export;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getLiteralId() {
		return literalId;
	}

	public void setLiteralId(String literalId) {
		this.literalId = literalId;
	}

	public ExportDefinition getExportDefinition() {
		return exportDefinition;
	}

	public void setExportDefinition(ExportDefinition exportDefinition) {
		this.exportDefinition = exportDefinition;
	}

	public String getAcquirerClass() {
		return acquirerClass;
	}

	public void setAcquirerClass(String acquirerClass) {
		this.acquirerClass = acquirerClass;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.export, this.fieldName);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final ExportField other = (ExportField) obj;
		return Objects.equals(this.export, other.export) && Objects.equals(this.fieldName, other.fieldName);
	}
}
