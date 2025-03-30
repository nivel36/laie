package es.nivel36.laie.ejb.core.file;

import java.nio.file.Path;

public class TemporalFileBucket extends AbstractFileBucket {

	private static final TemporalFileBucket INSTANCE = new TemporalFileBucket();

	private TemporalFileBucket() {
		super("temp", Path.of("temp"));
	}

	public static TemporalFileBucket getInstance() {
		return INSTANCE;
	}
}
