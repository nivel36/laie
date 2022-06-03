package es.nivel36.laie.ejb.core.action;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.ejb.core.AbstractEvent;
import es.nivel36.laie.ejb.core.EventType;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "entityName", "type", "userId", "date" }))
public class Action extends AbstractEvent {

	private static final long serialVersionUID = 9068445509149161220L;

	@NotNull
	@Column(nullable = false)
	private Long entityId;

	@NotNull
	@Column(nullable = false)
	private String entityName;

	@NotNull
	@Column(nullable = false)
	private String entityTitle;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(nullable = false)
	private ActionType type;

	public Long getEntityId() {
		return entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getEntityTitle() {
		return entityTitle;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	public void setEntityTitle(String entityTitle) {
		this.entityTitle = entityTitle;
	}
	
	public void setType(ActionType type) {
		this.type = type;
	}

	@Override
	public EventType getType() {
		return this.type;
	}

	@Override
	public Auditable getEntity() {
		return null;
	}

}
