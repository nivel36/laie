/*
* Laie
* Copyright (C) 2021  Abel Ferrer
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.nivel36.laie.file;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Objects;

abstract class AbstractDigestedFileWriter implements DigestedFileWriter {

	protected final Path path;

	public AbstractDigestedFileWriter(final Path path) throws IOException {
		Objects.requireNonNull(path);
		this.path = path;
		final Path parent = path.getParent();
		if (!Files.exists(parent)) {
			Files.createDirectories(parent);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractDigestedFileWriter other = (AbstractDigestedFileWriter) obj;
		return Objects.equals(path, other.path);
	}

	protected abstract MessageDigest getMessageDigest();

	@Override
	public int hashCode() {
		return Objects.hash(path);
	}

	@Override
	public String write(final OutputStream outputStream) throws IOException {
		Objects.requireNonNull(outputStream);
		final MessageDigest messageDigest = this.getMessageDigest();
		final DigestOutputStream digestOutputStream = new DigestOutputStream(outputStream, messageDigest);
		Files.copy(this.path, digestOutputStream);
		final byte[] hash = messageDigest.digest();
		return Base64.getEncoder().encodeToString(hash);
	}
}
