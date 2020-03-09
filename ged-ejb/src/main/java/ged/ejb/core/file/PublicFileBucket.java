package ged.ejb.core.file;

import java.nio.file.Path;

class PublicFileBucket extends AbstractFileBucket {

	PublicFileBucket() {
		super("public", Path.of("public"));
	}
}
