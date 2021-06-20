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
package es.nivel36.laie.service.file;

import java.io.IOException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * Writes a file to the file system and obtains its hash.
 * </p>
 * 
 * @author Abel Ferrer
 *
 */
final class Sha256DigestedFileWriter extends AbstractDigestedFileWriter {

	private static final String SHA_256 = "SHA-256";

	private final MessageDigest messageDigest;

	public Sha256DigestedFileWriter(final Path path) throws IOException, NoSuchAlgorithmException {
		super(path);
		this.messageDigest = MessageDigest.getInstance(SHA_256);
	}

	@Override
	protected MessageDigest getMessageDigest() {
		return messageDigest;
	}
}
