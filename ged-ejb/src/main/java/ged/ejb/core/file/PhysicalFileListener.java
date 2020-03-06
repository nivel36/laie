package ged.ejb.core.file;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.PreRemove;

public class PhysicalFileListener {
	
	@PreRemove
    private void preRemove(PhysicalFile file){
		try {
			final Path path = Paths.get(file.getAbsolutePath());
			Files.deleteIfExists(path);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}		     
    }
}
