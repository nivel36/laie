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

public class FileDtoMapper {

	private File file;

	public FileDtoMapper(File file) {
		this.file = file;
	}

	public FileDto map() {
		if (this.file == null) {
			return null;
		}
		final FileDto fileDto = new FileDto();
		fileDto.setCreated(file.getCreated());
		fileDto.setDescription(file.getDescription());
		fileDto.setId(file.getId());
		fileDto.setName(file.getName());
		return fileDto;
	}
}
