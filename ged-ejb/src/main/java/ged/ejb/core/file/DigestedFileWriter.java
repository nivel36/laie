package ged.ejb.core.file;

import java.io.InputStream;
import java.nio.file.Path;

public interface DigestedFileWriter {

	String write(Path path, InputStream inputStream);

}
