package ged.rest.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;
import ged.ejb.user.role.RoleService;
import ged.rest.AbstractRestController;
import io.swagger.annotations.Api;

@Path("users")
@Api("users")
@ApplicationScoped
public class UserRestController extends AbstractRestController {

	@Inject
	private RoleService roleService;

	@Inject
	private UserService userService;

	public UserDto createUserDtoFromUser(final User user) {
		Objects.requireNonNull(user);
		final UserDto userDto = new UserDto();
		userDto.setDateOfJoin(user.getDateOfJoin());
		userDto.setEmail(user.getEmail());
		userDto.setImageFileName(user.getImageFileName());
		userDto.setLanguage(user.getLanguage());
		userDto.setLastConnection(user.getLastConnection());
		if (user.getManager() != null) {
			userDto.setManagerEmail(user.getManager().getEmail());
		}
		userDto.setName(user.getName());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setRoleName(user.getRole().getName());
		userDto.setSurename(user.getSurename());
		return userDto;
	}

	private List<UserDto> createUserDtoListFromUserList(final List<User> users) {
		final List<UserDto> userDtos = new ArrayList<>(users.size());
		for (final User user : users) {
			final UserDto userDto = this.createUserDtoFromUser(user);
			userDtos.add(userDto);
		}
		return userDtos;
	}

	private User createUserFromUserDto(final UserDto userDto) {
		final User user = new User();
		user.setDateOfJoin(userDto.getDateOfJoin());
		user.setEmail(userDto.getEmail());
		user.setImageFileName(userDto.getImageFileName());
		user.setLanguage(userDto.getLanguage());
		if (user.getManager() != null) {
			final User manager = this.userService.findUserByEmail(userDto.getManagerEmail());
			user.setManager(manager);
		}
		user.setName(userDto.getName());
		user.setPhoneNumber(userDto.getPhoneNumber());
		final List<Role> roles = this.roleService.findAllRoles();
		for (final Role role : roles) {
			if (role.getName().equals(userDto.getRoleName())) {
				user.setRole(role);
			}
		}
		user.setSurename(userDto.getSurename());
		return user;
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserDto find(@PathParam("id") final long id) {
		final User user = this.userService.find(id);
		final UserDto userDto = this.createUserDtoFromUser(user);
		return userDto;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDto> findAll() {
		final List<User> users = this.userService.findAll();
		final List<UserDto> userDtos = this.createUserDtoListFromUserList(users);
		return userDtos;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(final UserDto userDto) {
		Response.ResponseBuilder builder;
		final User user = this.createUserFromUserDto(userDto);
		try {
			this.validate(userDto);
			final User savedUser = this.userService.save(user);
			final UserDto returnedUserDto = this.createUserDtoFromUser(savedUser);
			builder = Response.status(Response.Status.OK).entity(returnedUserDto);
		} catch (final ConstraintViolationException ce) {
			builder = this.createViolationResponse(ce.getConstraintViolations());
		} catch (final ValidationException e) {
			final Map<String, String> responseObj = new HashMap<>();
			responseObj.put("email", "Email taken");
			builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
		} catch (final Exception e) {
			final Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		}

		return builder.build();
	}

	public void setRoleService(final RoleService roleService) {
		this.roleService = roleService;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}