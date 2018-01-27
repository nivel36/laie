package ged.web.core.view;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class MenuBeanTest {

	private MenuBean menuBean;

	@Test
	public void gotoCandidatesTest() {
		final String url = this.menuBean.gotoCandidates();
		Assert.assertNotNull(url);
	}

	@Test
	public void gotoClientsTest() {
		final String url = this.menuBean.gotoClients();
		Assert.assertNotNull(url);
	}

	@Test
	public void gotoIndexTest() {
		final String url = this.menuBean.gotoIndex();
		Assert.assertNotNull(url);
	}

	@Test
	public void gotoJobOffersTest() {
		final String url = this.menuBean.gotoJobOffers();
		Assert.assertNotNull(url);
	}

	@Test
	public void gotoMaintenancesTest() {
		final String url = this.menuBean.gotoMaintenances();
		Assert.assertNotNull(url);
	}

	@Test
	public void gotoReportsTest() {
		final String url = this.menuBean.gotoReports();
		Assert.assertNotNull(url);
	}

	@Test
	public void gotoUsersTest() {
		final String url = this.menuBean.gotoUsers();
		Assert.assertNotNull(url);
	}

	@Before
	public void setUp() {
		this.menuBean = new MenuBean();
	}
}
