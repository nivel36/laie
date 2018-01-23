package ged.web.core;

import java.io.Serializable;
import java.util.Map;

public class Page implements Serializable {

	public enum RequestType {
		GET, POST
	}

	private static final long serialVersionUID = 4925284432001173093L;

	private String name;

	private Map<String, Object> params;

	private RequestType requestType;

	private String url;

	public String getName() {
		return this.name;
	}

	public Map<String, Object> getParams() {
		return this.params;
	}

	public RequestType getRequestType() {
		return this.requestType;
	}

	public String getUrl() {
		return this.url;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setParams(final Map<String, Object> params) {
		this.params = params;
	}

	public void setRequestType(final RequestType requestType) {
		this.requestType = requestType;
	}

	public void setUrl(final String url) {
		this.url = url;
	}
}