package ged.web.core.view;

import org.junit.Before;
import org.junit.Test;

import ged.web.core.util.Navigate;
import ged.web.core.util.Page;

import org.junit.Assert;

public class MenuBeanTest {

	private MenuBean menuBean;

	@Test
	public void gotoCandidatesTest() {
		final String url = this.menuBean.gotoCandidates();
		Assert.assertEquals(Page.CANDIDATE_SEARCH.url() + "?" + Navigate.FACES_REDIRECT, url);
	}

	@Test
	public void gotoClientsTest() {
		final String url = this.menuBean.gotoClients();
		Assert.assertEquals(Page.CLIENT_SEARCH.url() + "?" + Navigate.FACES_REDIRECT, url);
	}

	@Test
	public void gotoIndexTest() {
		final String url = this.menuBean.gotoIndex();
		Assert.assertEquals(Page.INDEX.url() + "?" + Navigate.FACES_REDIRECT, url);
	}

	@Test
	public void gotoJobOffersTest() {
		final String url = this.menuBean.gotoJobOffers();
		Assert.assertEquals(Page.JOB_OFFER_SEARCH.url() + "?" + Navigate.FACES_REDIRECT, url);
	}

	@Test
	public void gotoMaintenancesTest() {
		final String url = this.menuBean.gotoMaintenances();
		Assert.assertEquals(Page.MAINTENANCE.url() + "?" + Navigate.FACES_REDIRECT, url);
	}

	@Test
	public void gotoReportsTest() {
		final String url = this.menuBean.gotoReports();
		Assert.assertEquals(Page.REPORT.url() + "?" + Navigate.FACES_REDIRECT, url);
	}

	@Test
	public void gotoUsersTest() {
		final String url = this.menuBean.gotoUsers();
		Assert.assertEquals(Page.USER_SEARCH.url() + "?" + Navigate.FACES_REDIRECT, url);
	}

	@Before
	public void setUp() {
		this.menuBean = new MenuBean();
	}
}
