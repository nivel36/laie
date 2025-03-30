package es.nivel36.laie.ejb.core.file;

import java.nio.file.Path;

class PublicFileBucket extends AbstractFileBucket {

	private final static PublicFileBucket INSTANCE = new PublicFileBucket();

	private PublicFileBucket() {
		super("public", Path.of("public"));
	}

	public static PublicFileBucket getInstance() {
		return INSTANCE;
	}
}
