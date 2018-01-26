package ged.api.v1.user;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ged.api.v1.AbstractRestController;
import ged.api.v1.mapper.Mapper;
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
		final User user = this.userService.find(id);
		final UserDto userDto = this.userMapper.mapEntity(user);
		return userDto;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDto> findAll() {
		final List<User> users = this.userService.findAll();
		final List<UserDto> userDtos = createUserDtoListFromUserList(users);
		return userDtos;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(@Valid final UserDto userDto) {
		Response.ResponseBuilder builder;
		final User user = this.userMapper.mapDto(userDto);
		final User savedUser = this.userService.save(user);
		final UserDto returnedUserDto = this.userMapper.mapEntity(savedUser);
		builder = Response.status(Response.Status.OK).entity(returnedUserDto);
		return builder.build();
	}

	public void setUserMapper(final UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}