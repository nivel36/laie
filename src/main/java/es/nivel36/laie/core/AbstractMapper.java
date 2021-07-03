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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.nivel36.laie.core.service.AbstractEntity;

/**
 * Abstract class from which all application mappers will inherit.
 * 
 * <p>
 * To implement a mapper, the programmer needs only to extend this class and
 * provide implementations for the {@link #map(I)} method.
 * 
 * @author Abel Ferrer
 */
public abstract class AbstractMapper<I extends AbstractEntity, O extends Dto> implements Mapper<I, O> {

	/**
	 * Map a list of elements.
	 * 
	 * @param list <tt>List</tt> of elements to map.
	 * @return <tt>List</tt> of the mapped elements
	 */
	public List<O> mapList(final List<I> list) {
		final int size = list.size();
		final List<O> returnList = new ArrayList<>(size);
		for (final I i : list) {
			final O map = this.map(i);
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * Map a set of elements.
	 * 
	 * @param set <tt>Set</tt> of elements to map.
	 * @return <tt>Set</tt> of the mapped elements
	 */
	public Set<O> mapSet(final Set<I> set) {
		final int size = set.size();
		final Set<O> returnSet = new HashSet<>(size);
		for (final I i : set) {
			final O map = this.map(i);
			returnSet.add(map);
		}
		return returnSet;
	}
}
