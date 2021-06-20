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
package es.nivel36.laie.service;

public interface Identifiable {

	/**
	 * Un identificador único en toda la aplicación. Dos objectos identificables
	 * solo pueden tener el mismo identificador si son el mismo objeto. Este
	 * identificador es independiente de la clase. Dos objetos de clases diferentes
	 * no pueden tener el mismo identificador.
	 * 
	 * @return <tt>String</tt> con el identificador único
	 */
	String getUid();
}
