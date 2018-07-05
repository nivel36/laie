package ged.web.core.util;

import ged.ejb.core.util.Parameters;

public class Page {

	public enum Type {
		GET, POST
	};

	private final Type type;

	private final String url;

	public Page(final String url, final Type type) {
		this.url = url;
		this.type = type;
	}

	public void go() {
		if (this.type == Type.POST) {
			Navigate.to(this.url).doPost();
		}
		else {
			Navigate.to(this.url).doGet();
		}
	}

	public void go(final String id, final Object value) {
		if (this.type == Type.POST) {
			Navigate.to(this.url).withParams(Parameters.map(id, value)).doPost();
		}
		else {
			Navigate.to(this.url).withParams(Parameters.map(id, value)).doGet();
		}
	}
}
