package es.nivel36.laie.ejb.core.file;

import java.nio.file.Path;

class PrivateFileBucket extends AbstractFileBucket {
	
	private final static PrivateFileBucket INSTANCE = new PrivateFileBucket();

	private PrivateFileBucket() {
		super("private", Path.of("private"));
	}
	
	public static PrivateFileBucket getInstance() {
		return INSTANCE;
	}
}
