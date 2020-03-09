package ged.ejb.core;

public enum Language {

	Catalan("ca"), English("en"), Spanish("es");

	public static Language ofCode(final String code) {
		for (final Language language : values()) {
			if (language.code.equals(code)) {
				return language;
			}
		}
		return null;
	}

	private String code;

	Language(final String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}
}
