package es.nivel36.laie.ejb.job.offer;

import java.io.Serializable;
import java.util.Objects;

public class SimpleJobOfferDto implements Serializable {

	private static final long serialVersionUID = -8518219599742781303L;

	private JobOfferState state;

	private String title;
	
	private String uid;

	public JobOfferState getState() {
		return state;
	}

	public String getTitle() {
		return title;
	}
	
	public String getUid() {
		return uid;
	}

	public void setState(JobOfferState state) {
		this.state = state;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleJobOfferDto other = (SimpleJobOfferDto) obj;
		return state == other.state && Objects.equals(title, other.title);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(state, title);
	}

	@Override
	public String toString() {
		return title;
	}
}
