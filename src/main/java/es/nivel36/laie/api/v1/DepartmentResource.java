package es.nivel36.laie.api.v1;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
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

import es.nivel36.laie.department.DepartmentDto;
import es.nivel36.laie.department.DepartmentService;
import es.nivel36.laie.user.UserDto;

/**
 * The class dedicated to support the department api in the application.
 * 
 * @author Abel Ferrer
 *
 */
@Path("/v1/department")
public class DepartmentResource extends AbstractResource {

	////////////////////////////////////////////////////////////////////////////
	// VARIABLES
	////////////////////////////////////////////////////////////////////////////

	@Inject
	private DepartmentService departmentService;

	////////////////////////////////////////////////////////////////////////////
	// PUBLIC
	////////////////////////////////////////////////////////////////////////////

	/**
	 * Searches for all departments.
	 *
	 * @param page     <tt>int</tt> with the page to start searching from. Must be
	 *                 greater or equal than 0.
	 * @param pageSize <tt>int</tt> with the maximum number of results to return.
	 *                 Must be greater than 0 and lesser or equal than 100.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public List<DepartmentDto> findAll(@QueryParam("page") final int page, @QueryParam("pageSize") final int pageSize) {
		this.validatePagination(page, pageSize);
		final List<DepartmentDto> departments = this.departmentService.findAll(page, pageSize);
		if (departments.isEmpty()) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return departments;
	}

	/**
	 * Searches for all users in the department that is passed as a parameter.
	 *
	 * @param departmentUid <tt>String</tt> with the department's identifier. This
	 *                      parameter cannot be null.
	 * @param page          <tt>int</tt> with the page to start searching from. Must
	 *                      be greater or equal than 0.
	 * @param pageSize      <tt>int</tt> with the maximum number of results to
	 *                      return. Must be greater than 0 and lesser or equal than
	 *                      100.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{uid}/user")
	public List<UserDto> findAllWorkersOfDepartment(@PathParam("uid") final String departmentUid,
			@QueryParam("page") final int page, @QueryParam("pageSize") final int pageSize) {
		Objects.requireNonNull(departmentUid);
		this.validatePagination(page, pageSize);
		final List<UserDto> users = this.departmentService.findAllWorkersOfDepartment(departmentUid, page, pageSize);
		if (users.isEmpty()) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return users;
	}

	/**
	 * Search for a department by their unique identifier.
	 *
	 * @param uid <tt>String</tt> with the unique identifier. This parameter cannot
	 *            be null.
	 *
	 * @return <tt>Department</tt> that has the same identifier as the one passed as
	 *         parameter.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{uid}")
	public DepartmentDto findDepartmentByUid(@PathParam("uid") final String departmentUid) {
		Objects.requireNonNull(departmentUid);
		final DepartmentDto department = this.departmentService.findDepartmentByUid(departmentUid);
		if (department == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return department;
	}

	/**
	 * Insert a department in the database.
	 *
	 * @param department <tt>Department</tt> to insert. This parameter cannot be
	 *                   null
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	public DepartmentDto insert(final DepartmentDto department) {
		Objects.requireNonNull(department);
		return this.departmentService.insert(department);
	}

	/**
	 * Update a department in the database.
	 *
	 * @param department <tt>Department</tt> to update. This parameter cannot be
	 *                   null
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	public void update(final DepartmentDto department) {
		Objects.requireNonNull(department);
		departmentService.update(department);
	}
}