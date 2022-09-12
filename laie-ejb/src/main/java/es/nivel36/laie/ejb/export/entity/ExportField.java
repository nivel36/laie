package es.nivel36.laie.ejb.export.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.nivel36.laie.ejb.core.model.AbstractEntity;

@Entity
@Table(name = "EXPORT_FIELD")
public class ExportField extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	// TODO ivmedina unique export-fieldName
	// TODO ivmedina unique export-sortOrder

	@ManyToOne
	@JoinColumn(name = "EXPORT_ID", nullable = false)
	private Export export;

	@Column(name = "FIELD_NAME", length = 64, nullable = false)
	private String fieldName;

	@Column(name = "SORT_ORDER", scale = 3, precision = 0, nullable = false)
	private int sortOrder;

	@Column(name = "LITERAL_ID", length = 64, nullable = false)
	private String literalId;

	@Column(name = "ACQUIRER_CLASS", length = 256, nullable = false)
	private String acquirerClass;

	@Column(name = "DISABLED", nullable = false)
	private boolean disabled;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "exportField")
	@JoinColumn(name = "EXPORT_DEFINITION_ID")
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
