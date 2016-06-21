package ged.web.view;

public class ActionDto {

	private String text;

	private String url;

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
