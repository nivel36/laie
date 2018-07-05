package ged.web.core.view;

import static ged.web.core.util.Navigate.FACES_REDIRECT;
import static ged.web.core.util.PageEnum.CANDIDATE_SEARCH;
import static ged.web.core.util.PageEnum.CLIENT_SEARCH;
import static ged.web.core.util.PageEnum.INDEX;
import static ged.web.core.util.PageEnum.JOB_OFFER_SEARCH;
import static ged.web.core.util.PageEnum.MAINTENANCE;
import static ged.web.core.util.PageEnum.REPORT;
import static ged.web.core.util.PageEnum.USER_SEARCH;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MenuBeanTest {

	private MenuBean menuBean;

	@Test
	public void gotoCandidatesTest() {
		final String url = this.menuBean.gotoCandidates();
		Assert.assertEquals(CANDIDATE_SEARCH.url() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void gotoClientsTest() {
		final String url = this.menuBean.gotoClients();
		Assert.assertEquals(CLIENT_SEARCH.url() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void gotoIndexTest() {
		final String url = this.menuBean.gotoIndex();
		Assert.assertEquals(INDEX.url() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void gotoJobOffersTest() {
		final String url = this.menuBean.gotoJobOffers();
		Assert.assertEquals(JOB_OFFER_SEARCH.url() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void gotoMaintenancesTest() {
		final String url = this.menuBean.gotoMaintenances();
		Assert.assertEquals(MAINTENANCE.url() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void gotoReportsTest() {
		final String url = this.menuBean.gotoReports();
		Assert.assertEquals(REPORT.url() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void gotoUsersTest() {
		final String url = this.menuBean.gotoUsers();
		Assert.assertEquals(USER_SEARCH.url() + "?" + FACES_REDIRECT, url);
	}

	@Before
	public void setUp() {
		this.menuBean = new MenuBean();
	}
}
