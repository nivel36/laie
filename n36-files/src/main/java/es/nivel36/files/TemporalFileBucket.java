package es.nivel36.files;

import java.nio.file.Path;

public class TemporalFileBucket extends AbstractFileBucket {
	
	private final static TemporalFileBucket INSTANCE = new TemporalFileBucket();

	private TemporalFileBucket() {
		super("temp", Path.of("temp"));
	}
	
	public static TemporalFileBucket getInstance() {
		return INSTANCE;
	}
}
