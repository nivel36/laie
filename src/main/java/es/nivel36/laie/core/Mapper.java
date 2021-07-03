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
package es.nivel36.laie.core;

import es.nivel36.laie.core.service.AbstractEntity;

/**
 * Interface to be implemented by any Mapper.
 * 
 * @author Abel Ferrer
 *
 */
public interface Mapper<I extends AbstractEntity, O extends Dto> {

	/**
	 * Map one class to another. The fields to which values are transferred is
	 * determined by the implementation of the method.
	 * 
	 * @param object <tt>I</tt> to map.
	 * @return <tt>O</tt> with the mapped object.
	 */
	O map(I object);
}
