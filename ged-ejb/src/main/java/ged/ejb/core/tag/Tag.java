package ged.ejb.core.tag;

import java.util.ArrayList;
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

	@ManyToOne
	@JoinColumn(name = "parentId")
	private Tag parent;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true)
	private List<Tag> children = new ArrayList<Tag>();

	@NotNull
	@Column(length = 128, nullable = false)
	private String label;

}
