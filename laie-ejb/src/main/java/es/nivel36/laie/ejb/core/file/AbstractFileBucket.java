package es.nivel36.laie.ejb.core.file;

import java.nio.file.Path;

abstract class AbstractFileBucket implements FileBucket {

	private final String name;

	private final Path path;

	AbstractFileBucket(final String name, final Path path) {
		this.name = name;
		this.path = path;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Path getPath() {
		return this.path;
	}

}
