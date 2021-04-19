package ged.ejb.core.file;

import java.nio.file.Path;

class PrivateFileBucket extends AbstractFileBucket {

	PrivateFileBucket() {
		super("private", Path.of("private"));
	}
}
