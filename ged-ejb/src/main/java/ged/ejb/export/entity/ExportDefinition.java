package ged.ejb.export.entity;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import ged.ejb.core.model.AbstractEntity;

@Entity
public class ExportDefinition extends AbstractEntity {

	private static final long serialVersionUID = 2536514027188561441L;

	// TODO ivmedina unique export-exportField
	// TODO ivmedina unique export-sortOrder
	
	@ManyToOne
	@JoinColumn(name = "exportId", nullable = false)
	private Export export;
	
	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "exportFieldId", nullable = false, unique = true)
	private ExportField exportField;
	
	@Column(scale = 6, precision = 0, nullable = false)
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
