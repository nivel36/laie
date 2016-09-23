package ged.ejb.core.tag;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import ged.ejb.core.model.AbstractAuditedEntity;

@Entity
public class Tag extends AbstractAuditedEntity {

	private static final long serialVersionUID = -2676859619371128798L;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true)
	private List<Tag> children;

	@NotNull
	@Column(length = 128, nullable = false)
	private String label;

	@ManyToOne
	@JoinColumn(name = "parentId")
	private Tag parent;

	public List<Tag> getChildren() {
		return this.children;
	}

	public String getLabel() {
		return this.label;
	}

	public Tag getParent() {
		return this.parent;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public void setParent(final Tag parent) {
		this.parent = parent;
	}
}