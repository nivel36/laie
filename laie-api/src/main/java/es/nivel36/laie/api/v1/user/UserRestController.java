package es.nivel36.laie.api.v1.user;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.nivel36.laie.api.v1.AbstractRestController;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.user.BadManagerException;
import es.nivel36.laie.ejb.user.DuplicateEmailException;
import es.nivel36.laie.ejb.user.UserDto;
import es.nivel36.laie.ejb.user.UserService;

@Path("user")
@ApplicationScoped
public class UserRestController extends AbstractRestController {

	@Inject
	private UserService userService;

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(@Valid final UserDto userDto, String manangerUid) {
		Objects.requireNonNull(userDto);
		Response.ResponseBuilder builder;
		try {
			this.userService.addUser(userDto, manangerUid);
			builder = Response.status(Response.Status.OK);
		} catch (final DuplicateEmailException exception) {
			builder = Response.status(Response.Status.BAD_REQUEST);
		}
		return builder.build();
	}

	@GET
	@Path("/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserDto findUserByEmail(@PathParam("email") final String email) {
		Objects.requireNonNull(email);
		return this.userService.findUserByUid(email);
	}

	@GET
	@Path("/{uid:[0-9a-fA-F]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserDto findUserByUid(@PathParam("uid") final String uid) {
		Objects.requireNonNull(uid);
		return this.userService.findUserByUid(uid);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(@Valid final UserDto userDto) {
		Objects.requireNonNull(userDto);
		Response.ResponseBuilder builder;
		try {
			this.userService.updateUser(userDto);
			builder = Response.status(Response.Status.OK);
		} catch (final DuplicateEmailException e) {
			builder = Response.status(Response.Status.BAD_REQUEST);
		}
		return builder.build();
	}

	@POST
	@Path("{uid:[0-9a-fA-F]+}/changeImage")
	public String changeUsersImage(@PathParam("uid") final String userUid, final InputStream image) {
		return this.userService.changeUsersImage(userUid, image);

	}

	@POST
	@Path("{uid:[0-9a-fA-F]+}/deleteImage")
	public void deleteUsersImage(@PathParam("uid") final String userUid) {
		this.userService.deleteUsersImage(userUid);
	}

	@POST
	@Path("{uid:[0-9a-fA-F]+}/changeManager/")
	public Response changeUsersManager(@PathParam("uid") final String userUid,
			@QueryParam("managerUid") final String managerUid) {
		Response.ResponseBuilder builder;
		try {
			userService.changeUsersManager(userUid, managerUid);
			builder = Response.status(Response.Status.OK);
		} catch (BadManagerException e) {
			builder = Response.status(Response.Status.BAD_REQUEST);
		}
		return builder.build();
	}

	@POST
	@Path("changePassword")
	public void changePassword(@QueryParam("email") final String email,
			@QueryParam("oldPassword") final String oldPassword, @QueryParam("newPassword") final String newPassword) {
		this.userService.changePassword(email, oldPassword, newPassword);
	}

	@GET
	@Path("{uid:[0-9a-fA-F]+}/subordinate/")
	public List<UserDto> findSubordinateUsers(@PathParam("uid") String userUid) {
		return this.userService.findSubordinateUsers(userUid);
	}

	@GET
	@Path("search/")
	public SearchResult<UserDto> search(@QueryParam("searchText") final String searchText,
			@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
		final Page page = new Page(offset, limit);
		return this.userService.search(searchText, page);
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}