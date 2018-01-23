package ged.web.core.view;

public class Navigation {

	private String label;

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
		final Navigation other = (Navigation) obj;
		if (this.url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!this.url.equals(other.url)) {
			return false;
		}
		return true;
	}

	public String getLabel() {
		return this.label;
	}

	public String getUrl() {
		return this.url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.url == null ? 0 : this.url.hashCode());
		return result;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public void setUrl(final String url) {
		this.url = url;
	}
}