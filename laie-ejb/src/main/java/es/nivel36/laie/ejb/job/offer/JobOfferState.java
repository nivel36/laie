package es.nivel36.laie.ejb.job.offer;

import es.nivel36.laie.ejb.core.EventState;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "JOB_OFFER_STATE")
public class JobOfferState extends AbstractEntity implements EventState {

	private static final long serialVersionUID = -8009230673860174776L;

	@Column(name = "CLOSE")
	private boolean closeState;

	@Column(name = "NAME")
	private String name;

	@Column(name = "OPEN")
	private boolean openState;
	
	@Column(name = "COLOR")
	private String color;
	
	@Column(name = "BACKGROUND_COLOR")
	private String backgroundColor;
	

	@Override
	public String getName() {
		return this.name;
	}

	public boolean isCloseState() {
		return this.closeState;
	}

	public boolean isOpenState() {
		return this.openState;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
