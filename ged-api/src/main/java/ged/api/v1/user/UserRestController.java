package ged.api.v1.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ged.api.v1.AbstractRestController;
import ged.api.v1.mapper.Mapper;
import ged.ejb.core.model.Page;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import io.swagger.annotations.Api;

@Path("user")
@Api("user")
@ApplicationScoped
public class UserRestController extends AbstractRestController {

	@Inject
	@Mapper
	private UserMapper userMapper;

	@Inject
	private UserService userService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(@Valid final UserDto userDto) {
		final User user = this.userMapper.mapDto(userDto);
		final User savedUser = this.userService.save(user);
		final UserDto returnedUserDto = this.userMapper.mapEntity(savedUser);
		final Response.ResponseBuilder builder = Response.status(Response.Status.OK).entity(returnedUserDto);
		return builder.build();
	}

	private List<UserDto> createUserDtoListFromUserList(final List<User> users) {
		final List<UserDto> userDtos = new ArrayList<>(users.size());
		for (final User user : users) {
			final UserDto userDto = this.userMapper.mapEntity(user);
			userDtos.add(userDto);
		}
		return userDtos;
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserDto find(@PathParam("id") final long id) {
		Objects.requireNonNull(id);
		final User user = this.userService.find(id);
		return this.userMapper.mapEntity(user);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDto> findAll() {
		final List<User> users = this.userService.findAll(Page.ALL_RESULTS);
		return createUserDtoListFromUserList(users);
	}

	@GET
	@Path("/find")
	@Produces(MediaType.APPLICATION_JSON)
	public UserDto findByEmail(@QueryParam("email") final String email) {
		Objects.requireNonNull(email);
		final User user = this.userService.findUserByEmail(email);
		return this.userMapper.mapEntity(user);
	}

	public void setUserMapper(final UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(@Valid final UserDto userDto) {
		Objects.requireNonNull(userDto);
		final User user = this.userMapper.mapDto(userDto);
		final User savedUser = this.userService.save(user);
		final UserDto returnedUserDto = this.userMapper.mapEntity(savedUser);
		final Response.ResponseBuilder builder = Response.status(Response.Status.OK).entity(returnedUserDto);
		return builder.build();
	}
}