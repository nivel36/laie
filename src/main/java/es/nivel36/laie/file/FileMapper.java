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

import es.nivel36.laie.core.AbstractMapper;

public class FileMapper extends AbstractMapper<File, FileDto> {

	@Override
	public FileDto map(final File file) {
		if (file == null) {
			return null;
		}
		final FileDto fileDto = new FileDto();
		fileDto.setCreated(file.getCreated());
		fileDto.setDescription(file.getDescription());
		fileDto.setUid(file.getUid());
		fileDto.setName(file.getName());
		return fileDto;
	}
}
