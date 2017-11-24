package ged.rest.user;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ged.ejb.user.User;
import ged.ejb.user.UserService;

@Path("users")
@ApplicationScoped
public class UserRestController {

	@Inject
	private UserService userService;

	private List<UserDto> convertToUserDtoList(final List<User> users) {
		final List<UserDto> userDtos = new ArrayList<>(users.size());
		for (final User user : users) {
			final UserDto userDto = new UserDto(user);
			userDtos.add(userDto);
		}
		return userDtos;
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserDto find(@PathParam("id") final long id) {
		final User user = this.userService.find(id);
		final UserDto userDto = new UserDto(user);
		return userDto;
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDto> findAll() {
		final List<User> users = this.userService.findAll();
		final List<UserDto> userDtos = this.convertToUserDtoList(users);
		return userDtos;
	}
}