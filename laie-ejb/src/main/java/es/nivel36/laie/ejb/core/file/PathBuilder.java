package es.nivel36.laie.ejb.core.file;

import java.nio.file.Path;
import java.nio.file.Paths;

class PathBuilder {

	Path buildRelativePath(final FileBucket fileBucket, final String uId) {
		return Paths.get(fileBucket.getPath().toString(), uId.substring(0, 1), uId.substring(1, 2), uId);
	}

	Path buildAbsolutePath(final String root, final FileBucket fileBucket, final String uId) {
		return Paths.get(root, this.buildRelativePath(fileBucket, uId).toString());
	}

	Path buildAbsolutePath(final String root, final Path relativePath) {
		return Paths.get(root, relativePath.toString());
	}
}
