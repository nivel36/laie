package es.nivel36.laie.core;

public enum Language {

	CATALAN("ca"), ENGLISH("en"), SPANISH("es");

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
