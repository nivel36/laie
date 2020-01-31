package ged.ejb.core;

public enum Language {

	Spanish("es"), Catalan("ca"), English("en");

	private String code;

	Language(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static Language ofCode(String code) {
		for (Language language : values()) {
			if (language.code.equals(code)) {
				return language;
			}
		}
		return null;
	}
}
