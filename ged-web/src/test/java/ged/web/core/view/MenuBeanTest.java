package ged.web.core.view;

import static ged.web.core.util.Navigate.FACES_REDIRECT;
import static ged.web.core.util.PageEnum.CANDIDATE_SEARCH;
import static ged.web.core.util.PageEnum.CLIENT_SEARCH;
import static ged.web.core.util.PageEnum.INDEX;
import static ged.web.core.util.PageEnum.JOB_OFFER_SEARCH;
import static ged.web.core.util.PageEnum.MAINTENANCE;
import static ged.web.core.util.PageEnum.REPORT;
import static ged.web.core.util.PageEnum.USER_SEARCH;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class MenuBeanTest {

	private MenuBean menuBean;

	@Test
	public void gotoCandidatesTest() {
		final String url = this.menuBean.gotoCandidates();
		assertEquals(CANDIDATE_SEARCH.url() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void gotoClientsTest() {
		final String url = this.menuBean.gotoClients();
		assertEquals(CLIENT_SEARCH.url() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void gotoIndexTest() {
		final String url = this.menuBean.gotoIndex();
		assertEquals(INDEX.url() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void gotoJobOffersTest() {
		final String url = this.menuBean.gotoJobOffers();
		assertEquals(JOB_OFFER_SEARCH.url() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void gotoMaintenancesTest() {
		final String url = this.menuBean.gotoMaintenances();
		assertEquals(MAINTENANCE.url() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void gotoReportsTest() {
		final String url = this.menuBean.gotoReports();
		assertEquals(REPORT.url() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void gotoUsersTest() {
		final String url = this.menuBean.gotoUsers();
		assertEquals(USER_SEARCH.url() + "?" + FACES_REDIRECT, url);
	}

	@BeforeEach
	public void setUp() {
		this.menuBean = new MenuBean();
	}
}
