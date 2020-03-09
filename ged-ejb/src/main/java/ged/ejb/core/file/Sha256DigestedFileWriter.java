package ged.ejb.core.file;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

class Sha256DigestedFileWriter implements DigestedFileWriter {

	final MessageDigest digest;

	public Sha256DigestedFileWriter() {
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String write(Path path, InputStream inputStream) {
		try (DigestInputStream digestInputStream = new DigestInputStream(inputStream, digest)) {
			final Path parent = path.getParent();
			if (!Files.exists(parent)) {
				Files.createDirectories(parent);
			}
			Files.copy(digestInputStream, path, REPLACE_EXISTING);
			final byte[] hash = digest.digest();
			return Base64.getEncoder().encodeToString(hash);
		} catch (final IOException e) {
			throw new FileUploadException(e);
		}
	}
}
