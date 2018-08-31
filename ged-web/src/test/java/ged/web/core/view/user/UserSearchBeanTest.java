package ged.web.core.view.user;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ged.ejb.user.UserService;
import ged.web.view.user.UserSearchBean;

@ExtendWith(MockitoExtension.class)
public class UserSearchBeanTest {

	@Nested
	class Search {

		@Test
		public void shouldBeOk() {
			userSearchBean.setSearchText("abel");
			userSearchBean.clean();
			final String searchText = userSearchBean.getSearchText();
			assertNull(searchText);
		}
	}

	private UserSearchBean userSearchBean;

	@Mock
	private UserService userService;

	@BeforeEach
	public void setUp() {
		this.userSearchBean = new UserSearchBean();
		this.userSearchBean.setUserService(this.userService);
	}
}
