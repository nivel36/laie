package es.nivel36.laie.ejb.core.file;

import java.nio.file.Path;
import java.nio.file.Paths;

class PathBuilder {

	Path buildRelativePath(final FileBucket fileBucket, final String uuid) {
		return Paths.get(fileBucket.getPath().toString(), uuid.substring(0, 1), uuid.substring(1, 2), uuid);
	}

	Path buildAbsolutePath(final String root, final FileBucket fileBucket, final String uuid) {
		return Paths.get(root, this.buildRelativePath(fileBucket, uuid).toString());
	}

	Path buildAbsolutePath(final String root, final Path relativePath) {
		return Paths.get(root, relativePath.toString());
	}
}
