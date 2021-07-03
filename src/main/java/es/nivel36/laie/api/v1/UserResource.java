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
package es.nivel36.laie.api.v1;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.nivel36.laie.user.DuplicateEmailException;
import es.nivel36.laie.user.DuplicateIdNumberException;
import es.nivel36.laie.user.InvalidManagerException;
import es.nivel36.laie.user.UserDto;
import es.nivel36.laie.user.UserService;

/**
 * The class dedicated to support the user api in the application.
 * 
 * @author Abel Ferrer
 *
 */
@Path("/v1/user")
public class UserResource extends AbstractResource {

	////////////////////////////////////////////////////////////////////////////
	// VARIABLES
	////////////////////////////////////////////////////////////////////////////

	@Inject
	private UserService userService;

	////////////////////////////////////////////////////////////////////////////
	// PUBLIC
	////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns all users.
	 *
	 * @param page     <tt>int</tt> with the page to start searching from. Must be
	 *                 greater or equal than 0.
	 * @param pageSize <tt>int</tt> with the maximum number of results to return.
	 *                 Must be greater than 0 and lesser or equal than 100.
	 *
	 * @return <tt> List<UserDto></tt> with all the users. The size of the list is
	 *         the same as the size of the page.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public List<UserDto> findAll(@QueryParam("page") final int page, @QueryParam("pageSize") final int pageSize) {
		this.validatePagination(page, pageSize);
		final List<UserDto> users = this.userService.findAll(page, pageSize);
		if (users.isEmpty()) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return users;
	}

	/**
	 * Search for a user by their unique identifier.
	 *
	 * @param uid <tt>String</tt> with the unique identifier. This parameter cannot
	 *            be null.
	 *
	 * @return <tt>User</tt> that has the same identifier as the one passed as
	 *         parameter.
	 *
	 * @throws NoResultException if no user with such an identifier is found.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{uid}")
	public UserDto findUserByUid(@PathParam("uid") final String uid) {
		Objects.requireNonNull(uid);
		final UserDto user = this.userService.findUserByUid(uid);
		if (user == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return user;
	}

	/**
	 * Finds all users whose manager is the user passed as a parameter.
	 *
	 * @param managerUid <tt>String</tt> with the manager's identifier of all the
	 *                   users that will be returned in the query. This parameter
	 *                   cannot be null.
	 * @param page       <tt>int</tt> with the page to start searching from. Must be
	 *                   greater or equal than 0.
	 * @param pageSize   <tt>int</tt> with the maximum number of results to return.
	 *                   Must be greater than 0 and lesser or equal than 100
	 *
	 * @return <tt> List<UserDto></tt> whose manager is the user passed as a
	 *         parameter. The size of the list is the same as the size of the page.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{uid}/subordinate")
	public List<UserDto> findUsersByManager(@PathParam("uid") final String managerUid, @QueryParam("page") int page,
			@QueryParam("pageSize") final int pageSize) {
		Objects.requireNonNull(managerUid);
		this.validatePagination(page, pageSize);
		final List<UserDto> users = this.userService.findUsersByManager(managerUid, page, pageSize);
		if (users.isEmpty()) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return users;
	}

	/**
	 * Insert a user in the database.
	 *
	 * @param user <tt>UserDto</tt> to insert. This parameter cannot be null
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	public UserDto insert(final UserDto user) {
		Objects.requireNonNull(user);
		try {
			return this.userService.insert(user);
		} catch (DuplicateEmailException | DuplicateIdNumberException e) {
			throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
		}
	}

	/**
	 * Insert a user in the database.
	 *
	 * @param user <tt>UserDto</tt> to insert. This parameter cannot be null
	 *
	 * @return <tt>UserDto<tt> inserted.
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	public void update(final UserDto user) {
		Objects.requireNonNull(user);
		try {
			this.userService.update(user);
		} catch (DuplicateEmailException | DuplicateIdNumberException e) {
			throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
		}
	}

	/**
	 * Updates the user's manager.
	 *
	 * <p>
	 * The manager rules are:
	 * <ul>
	 * <li>A user cannot be his own manager.</li>
	 * <li>There can be no circular references. A user cannot have as a manager
	 * someone who has the user's own manager.</li>
	 * </ul>
	 * </p>
	 *
	 * @param userUid       <tt>String</tt> with the unique identifier of the user
	 *                      to which we are going to update the manager. This
	 *                      parameter cannot be null.
	 * @param newManagerUid <tt>String</tt> with the unique identifier of the new
	 *                      manager. <tt>null</tt> if we are going to remove the
	 *                      manager.
	 * @return <tt>UserDto</tt> with the new manager already informed.
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{uid}/updateManager")
	public UserDto updateManager(@PathParam("uid") final String userUid,
			@QueryParam("newManagerUid") final String newManagerUid) {
		Objects.requireNonNull(userUid);
		try {
			return this.userService.updateManager(userUid, newManagerUid);
		} catch (final InvalidManagerException e) {
			throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
		}
	}

	////////////////////////////////////////////////////////////////////////////
	// SETTER
	////////////////////////////////////////////////////////////////////////////

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}