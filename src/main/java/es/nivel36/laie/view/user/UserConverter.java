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
package es.nivel36.laie.view.user;

import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.user.UserDto;
import es.nivel36.laie.user.UserService;

/**
 * <p>
 * A converter that can perform User-to-String and String-to-User conversions.
 * </p>
 * 
 * @author Abel Ferrer
 */
@FacesConverter(managed = true, value = "userConverter")
public class UserConverter implements Converter<UserDto> {

	@Inject
	private UserService userService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDto getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		return this.userService.findUserByUid(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final UserDto value) {
		if (value == null) {
			return null;
		}
		return value.getUid();
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}