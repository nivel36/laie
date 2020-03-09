package ged.ejb.core.file;

import java.nio.file.Path;

interface FileBucket {

	String getName();

	Path getPath();
}
