package es.nivel36.laie.ejb.core.action;

import java.time.LocalDateTime;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ACTION", uniqueConstraints = @UniqueConstraint(columnNames = { "ENTITY_ID", "TYPE", "USER_ID", "DATE" }))
public class Action extends AbstractEntity {

	private static final long serialVersionUID = 9068445509149161220L;

	@NotNull
	@Column(name = "DATE", nullable = false)
	private LocalDateTime date;

	@NotNull
	@Column(name = "ENTITY_ID", nullable = false)
	private Long entityId;

	@NotBlank
	@Column(name = "ENTITY_NAME", nullable = false)
	private String entityName;

	@NotBlank
	@Column(name = "ENTITY_TITLE", nullable = false)
	private String entityTitle;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "TYPE", nullable = false)
	private ActionType type;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	public LocalDateTime getDate() {
		return date;
	}

	public Long getEntityId() {
		return entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getEntityTitle() {
		return entityTitle;
	}

	public ActionType getType() {
		return type;
	}

	public User getUser() {
		return user;
	}

	public void setDate(final LocalDateTime date) {
		this.date = date;
	}

	public void setEntityId(final Long entityId) {
		this.entityId = entityId;
	}

	public void setEntityName(final String entityName) {
		this.entityName = entityName;
	}

	public void setEntityTitle(final String entityTitle) {
		this.entityTitle = entityTitle;
	}

	public void setType(final ActionType type) {
		this.type = type;
	}

	public void setUser(final User user) {
		this.user = user;
	}

}
