package ged.web.view;

public class BookmarkDto {

	private String text;

	private String url;

	public BookmarkDto() {
	}

	public BookmarkDto(final String text, final String url) {
		this.text = text;
		this.url = url;
	}

	public String getText() {
		return this.text;
	}

	public String getUrl() {
		return this.url;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

}
