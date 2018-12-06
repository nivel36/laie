package ged.ejb.job.offer;

import javax.persistence.Entity;
import javax.persistence.Table;

import ged.ejb.core.maintenance.AbstractEnumEntity;

@Entity
@Table(name = "JOB_OFFER_STATE")
public class JobOfferState extends AbstractEnumEntity {

	private static final long serialVersionUID = -4884212859752082844L;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
