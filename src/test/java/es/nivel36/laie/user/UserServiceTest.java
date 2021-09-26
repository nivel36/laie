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
package es.nivel36.laie.user;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import es.nivel36.laie.department.DepartmentJpaDao;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends AbstractUserTest {

	@Nested
	class FindAll {

		@Test
		public void badPageShouldThrowIllegalArgumentException() {
			assertThrows(IllegalArgumentException.class, () -> {
				userService.findAll(-1, 2);
			});
		}

		@Test
		public void badPageSizeShouldThrowIllegalArgumentException() {
			assertThrows(IllegalArgumentException.class, () -> {
				userService.findAll(0, 0);
			});
		}

		@Test
		public void shouldReturnListOfUsers() {
			final List<User> list = Arrays.asList(mockUser("ABE1", null), mockUser("AFE1", null));
			Mockito.when(userJpaDao.findAll(0, 2)).thenReturn(list);

			final List<UserDto> users = userService.findAll(0, 2);
			Assertions.assertAll( // Assert all even if one fails.
					() -> Assertions.assertEquals(2, users.size()),
					() -> Assertions.assertEquals("ABE1", users.get(0).getUid()),
					() -> Assertions.assertEquals("AFE1", users.get(1).getUid()));
		}
	}

	@Nested
	class FindUserByUid {

		@Test
		public void nullUidShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userService.findUserByUid(null);
			});
		}

		@Test
		public void shouldReturnUser() {
			final User mockedUser = mockUser("ABE1", null);
			Mockito.when(userJpaDao.findUserByUid("ABE1")).thenReturn(mockedUser);

			final UserDto user = userService.findUserByUid("ABE1");

			Assertions.assertEquals("ABE1", user.getUid());
		}
	}

	@Nested
	class FindUsersByManager {

		@Test
		public void nullManagerShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userService.findUsersByManager(null, 0, 1);
			});
		}

		@Test
		public void badPageShouldThrowIllegalArgumentException() {
			assertThrows(IllegalArgumentException.class, () -> {
				userService.findUsersByManager("ABE1", -1, 1);
			});
		}

		@Test
		public void badPageSizeShouldThrowIllegalArgumentException() {
			assertThrows(IllegalArgumentException.class, () -> {
				userService.findUsersByManager("ABE1", 0, 0);
			});
		}

		@Test
		public void shouldReturnListOfUsers() {
			final List<User> list = Arrays.asList(mockUser("ABE1", null), mockUser("AFE1", null));
			Mockito.when(userJpaDao.findUsersByManager("AAA1", 0, 2)).thenReturn(list);

			final List<UserDto> users = userService.findUsersByManager("AAA1", 0, 2);
			Assertions.assertAll( // Assert all even if one fails.
					() -> Assertions.assertEquals(2, users.size()),
					() -> Assertions.assertEquals("ABE1", users.get(0).getUid()),
					() -> Assertions.assertEquals("AFE1", users.get(1).getUid()));
		}
	}

	@Nested
	class IsSubordinateUser {

		@Test
		public void nullUserUidShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userService.isSubordinateUser(null, "ABE1");
			});
		}

		@Test
		public void nullManagerUidShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userService.isSubordinateUser("ABE1", null);
			});
		}

	}

	@Nested
	class Insert {

		@Test
		public void nullUserShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userService.insert(null);
			});
		}

		@Test
		public void userWithDuplicateEmailShouldThrowDuplicateEmailException() {
			assertThrows(DuplicateEmailException.class, () -> {
				final User mockedUser = mockUser("AFE1", null);
				final UserDto mockedUserDto = mockUserDto("AFE1", null);
				Mockito.when(userJpaDao.findDuplicateEmail(mockedUser.getEmail())).thenReturn(true);

				userService.insert(mockedUserDto);
			});
		}

		@Test
		public void userWithDuplicateIdNumberShouldThrowDuplicateIdNumberException() {
			assertThrows(DuplicateIdNumberException.class, () -> {
				final User mockedUser = mockUser("AFE1", null);
				final UserDto mockedUserDto = mockUserDto("AFE1", null);
				Mockito.when(userJpaDao.findDuplicateEmail(mockedUser.getEmail())).thenReturn(false);
				Mockito.when(userJpaDao.findDuplicateIdNumber(mockedUser.getIdNumber())).thenReturn(true);

				userService.insert(mockedUserDto);
			});
		}

		@Test
		public void userWithoutManagerShouldReturnUserWithoutManager() throws Exception {
			final User mockedUser = mockUser("AFE1", null);
			final UserDto mockedUserDto = mockUserDto("AFE1", null);
			Mockito.when(userJpaDao.findDuplicateEmail(mockedUser.getEmail())).thenReturn(false);
			Mockito.when(userJpaDao.findDuplicateIdNumber(mockedUser.getIdNumber())).thenReturn(false);

			final UserDto returnedUser = userService.insert(mockedUserDto);
			Assertions.assertEquals("AFE1@email.com", returnedUser.getEmail());
			Assertions.assertEquals(null, returnedUser.getManager());
		}

		@Test
		public void userWithManagerShouldReturnUserWithManager() throws Exception {
			final User mockedManagerUser = mockUser("ABE1", null);
			final User mockedUser = mockUser("AFE1", mockedManagerUser);
			final UserDto mockedUserDto = mockUserDto("AFE1", mockSimpleUserDto("ABE1"));
			Mockito.when(userJpaDao.findDuplicateEmail(mockedUser.getEmail())).thenReturn(false);
			Mockito.when(userJpaDao.findDuplicateIdNumber(mockedUser.getIdNumber())).thenReturn(false);
			Mockito.when(userJpaDao.findUserByUid(mockedManagerUser.getIdNumber())).thenReturn(mockedManagerUser);

			final UserDto returnedUser = userService.insert(mockedUserDto);
			Assertions.assertEquals("AFE1@email.com", returnedUser.getEmail());
			Assertions.assertEquals("ABE1", returnedUser.getManager().getUid());
		}
	}

	@Nested
	class Update {

		@Test
		public void nullUserShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userService.update(null);
			});
		}

		@Test
		public void userWithDuplicateEmailShouldThrowDuplicateEmailException() {
			assertThrows(DuplicateEmailException.class, () -> {
				final User mockedUser = mockUser("AFE1", null);
				final UserDto mockedUserDto = mockUserDto("AFE1", null, "AFE@email.com");
				Mockito.when(userJpaDao.findUserByUid(mockedUserDto.getUid())).thenReturn(mockedUser);
				Mockito.when(userJpaDao.findDuplicateEmail(mockedUserDto.getEmail())).thenReturn(true);

				userService.update(mockedUserDto);
			});
		}

		@Test
		public void userWithDuplicateIdNumberShouldThrowDuplicateIdNumberException() {
			assertThrows(DuplicateIdNumberException.class, () -> {
				final User mockedUser = mockUser("AFE1", null);
				mockedUser.setIdNumber("idNumber");
				final UserDto mockedUserDto = mockUserDto("AFE1", null);
				Mockito.when(userJpaDao.findUserByUid(mockedUserDto.getUid())).thenReturn(mockedUser);
				Mockito.when(userJpaDao.findDuplicateIdNumber(mockedUserDto.getIdNumber())).thenReturn(true);
				
				userService.update(mockedUserDto);
			});
		}

		@Test
		public void userWithoutManagerShouldReturnUser() throws Exception {
			final User mockedUser = mockUser("AFE1", null);
			final UserDto mockedUserDto = mockUserDto("AFE1", null);
			Mockito.when(userJpaDao.findUserByUid(mockedUserDto.getIdNumber())).thenReturn(mockedUser);

			userService.update(mockedUserDto);
		}

		@Test
		public void userWithManagerRemovedManagerShouldReturnUserWithoutManager() throws Exception {
			final User mockedUser = mockUser("AFE1", mockUser("ABE1", null));
			final UserDto mockedUserDto = mockUserDto("AFE1", null);
			Mockito.when(userJpaDao.findUserByUid(mockedUserDto.getIdNumber())).thenReturn(mockedUser);

			userService.update(mockedUserDto);
		}
	}

	@Mock
	private UserJpaDao userJpaDao;

	@Mock
	private DepartmentJpaDao departmentJpaDao;

	private UserService userService;

	@BeforeEach
	public void setUp() {
		this.userService = new UserService();
		this.userService.setUserDao(this.userJpaDao);
		this.userService.setDepartmentDao(departmentJpaDao);
	}
}
