package ged.web.core.view.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ged.ejb.user.UserService;
import ged.web.view.user.UserSearchBean;

@RunWith(MockitoJUnitRunner.class)
public class UserSearchBeanTest {

	private UserSearchBean userSearchBean;

	@Mock
	private UserService userService;

	@Test
	public void cleanTest() {
		this.userSearchBean.setSearchText("abel");
		this.userSearchBean.clean();
		final String searchText = this.userSearchBean.getSearchText();
		Assert.assertNull(searchText);
	}

	@Before
	public void setUp() {
		this.userSearchBean = new UserSearchBean();
		this.userSearchBean.setUserService(this.userService);
	}
}
