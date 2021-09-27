package es.nivel36.laie.ejb.core.file;

import java.nio.file.Path;

interface FileBucket {

	String getName();

	Path getPath();
}
