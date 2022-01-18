package es.nivel36.laie.ejb.core.bookmark;

import java.io.Serializable;
import java.util.Objects;

public class BookmarkDto implements Serializable {

	private static final long serialVersionUID = -6999494932627734077L;

	public String title;

	public String url;

	public String imageUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(url);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		BookmarkDto other = (BookmarkDto) obj;
		return Objects.equals(url, other.url);
	}

	@Override
	public String toString() {
		return title;
	}
}