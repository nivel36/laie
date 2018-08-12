package ged.ejb.export;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import ged.ejb.core.model.AbstractEntity;

@Entity
public class Export extends AbstractEntity {

	private static final long serialVersionUID = 1529660983196543022L;

	@Column(length = 50, unique = true, nullable = false)
	private String exportName;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "export", orphanRemoval = true)
	private Set<ExportField> exportField;
	
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
