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

import java.nio.file.Path;

abstract class FileTest {

	public PhysicalFile mockPhysicalFile() {
		final Path path = Path.of("path");
		final PhysicalFile physicalFile = new PhysicalFile("uuid", path, "hash");
		physicalFile.setId(1L);
		return physicalFile;
	}
	
	public File mockFile() {
		final File file = new File("filename");
		file.setDescription("description");
		file.setId(1L);
		file.setUid("1");
		file.setPhysicalFile(mockPhysicalFile());
		return file;
	}
}
