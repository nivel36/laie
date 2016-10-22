package ged.ejb.core.bookmark;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import ged.ejb.core.model.AbstractRecordEntity;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "userId", "entityClass", "entityId" }))
public class Bookmark extends AbstractRecordEntity {

	private static final long serialVersionUID = 7897704476327486542L;

}