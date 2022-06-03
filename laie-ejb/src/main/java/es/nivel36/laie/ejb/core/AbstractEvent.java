package es.nivel36.laie.ejb.core;

import java.time.LocalDateTime;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.user.User;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractEvent extends AbstractEntity implements Event {

	private static final long serialVersionUID = 423120916523085069L;

	@Field(name = "date", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "date")
	protected LocalDateTime date;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	protected User user;

	@Override
	public LocalDateTime getDate() {
		return this.date;
	}

	@Override
	public User getUser() {
		return this.user;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
