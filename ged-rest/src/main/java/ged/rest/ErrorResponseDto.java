package ged.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorResponseDto {

	private String message;

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}
}
