package es.nivel36.laie.ejb.core.action;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.user.User;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "entityName", "type", "userId", "date" }))
public class Action extends AbstractEntity {

	private static final long serialVersionUID = 9068445509149161220L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@NotNull
	@Column(nullable = false)
	private LocalDateTime date;

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

	@NotNull
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
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

	public void setDate(LocalDateTime date) {
		this.date = date;
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

	public void setUser(User user) {
		this.user = user;
	}

}
