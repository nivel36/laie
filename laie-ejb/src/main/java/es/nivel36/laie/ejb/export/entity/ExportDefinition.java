package es.nivel36.laie.ejb.export.entity;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.nivel36.laie.ejb.core.model.AbstractEntity;

@Entity
@Table(name="EXPORT_DEFINITION")
public class ExportDefinition extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	// TODO ivmedina unique export-exportField
	// TODO ivmedina unique export-sortOrder

	@ManyToOne
	@JoinColumn(name = "EXPORT_ID", nullable = false)
	private Export export;

	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "EXPORT_FIELD_ID", nullable = false, unique = true)
	private ExportField exportField;

	@Column(name = "SORT_ORDER", scale = 3, precision = 0, nullable = false)
	private int sortOrder;

	public Export getExport() {
		return export;
	}

	public void setExport(Export export) {
		this.export = export;
	}

	public ExportField getExportField() {
		return exportField;
	}

	public void setExportField(ExportField exportField) {
		this.exportField = exportField;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.export, this.exportField);
	}

	@Override
	public boolean equals(final Object obj) {
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
		final ExportDefinition other = (ExportDefinition) obj;
		return Objects.equals(this.export, other.export) && Objects.equals(this.exportField, other.exportField);
	}
}
