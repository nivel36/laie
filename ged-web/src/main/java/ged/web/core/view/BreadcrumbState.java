package ged.web.core.view;

import java.io.Serializable;
import java.util.Map;

public class BreadcrumbState implements Serializable {

	private static final long serialVersionUID = -92576330486214205L;

	private transient Map<String, Object> state;

	private String title;

	private String url;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BreadcrumbState other = (BreadcrumbState) obj;
		if (this.title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!this.title.equals(other.title)) {
			return false;
		}
		return true;
	}

	public Map<String, Object> getState() {
		return this.state;
	}

	public String getTitle() {
		return this.title;
	}

	public String getUrl() {
		return this.url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.title == null) ? 0 : this.title.hashCode());
		return result;
	}

	public void setState(final Map<String, Object> state) {
		this.state = state;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setUrl(final String url) {
		this.url = url + "?faces-redirect=true";
	}

	@Override
	public String toString() {
		return this.title;
	}
}
