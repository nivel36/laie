package es.nivel36.laie.ejb.export.entity;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import es.nivel36.laie.ejb.core.model.AbstractEntity;

@Entity
@Table(name="EXPORT")
public class Export extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column(name="EXPORT_NAME", length = 64, unique = true, nullable = false)
	private String exportName;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "export", orphanRemoval = true)
	@OrderBy("sortOrder asc")
	private Set<ExportField> exportField;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "export", orphanRemoval = true)
	@OrderBy("sortOrder asc")
	private Set<ExportDefinition> exportDefinition;

	public String getExportName() {
		return exportName;
	}

	public void setExportName(String idExport) {
		this.exportName = idExport;
	}

	public Set<ExportField> getExportField() {
		return exportField;
	}

	public void setExportField(Set<ExportField> exportField) {
		this.exportField = exportField;
	}

	public Set<ExportDefinition> getExportDefinition() {
		return exportDefinition;
	}

	public void setExportDefinition(Set<ExportDefinition> exportDefinition) {
		this.exportDefinition = exportDefinition;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.exportName);
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
		final Export other = (Export) obj;
		return Objects.equals(this.exportName, other.exportName);
	}
}
