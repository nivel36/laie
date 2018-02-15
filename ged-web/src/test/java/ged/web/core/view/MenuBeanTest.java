package ged.web.core.view;

import org.junit.Before;
import org.junit.Test;

import ged.web.core.util.Navigate;

import org.junit.Assert;

public class MenuBeanTest {

	private MenuBean menuBean;

	@Test
	public void gotoCandidatesTest() {
		final String url = this.menuBean.gotoCandidates();
		Assert.assertEquals(Navigate.candidateSearchUrl(), url);
	}

	@Test
	public void gotoClientsTest() {
		final String url = this.menuBean.gotoClients();
		Assert.assertEquals(Navigate.clientSearchUrl(), url);
	}

	@Test
	public void gotoIndexTest() {
		final String url = this.menuBean.gotoIndex();
		Assert.assertEquals(Navigate.indexUrl(), url);
	}

	@Test
	public void gotoJobOffersTest() {
		final String url = this.menuBean.gotoJobOffers();
		Assert.assertEquals(Navigate.jobOfferSearchUrl(), url);
	}

	@Test
	public void gotoMaintenancesTest() {
		final String url = this.menuBean.gotoMaintenances();
		Assert.assertEquals(Navigate.maintenancesUrl(), url);
	}

	@Test
	public void gotoReportsTest() {
		final String url = this.menuBean.gotoReports();
		Assert.assertEquals(Navigate.reportsSearchUrl(), url);
	}

	@Test
	public void gotoUsersTest() {
		final String url = this.menuBean.gotoUsers();
		Assert.assertEquals(Navigate.userSearchUrl(), url);
	}

	@Before
	public void setUp() {
		this.menuBean = new MenuBean();
	}
}
