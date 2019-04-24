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

	@BeforeEach
	public void setUp() {
		this.menuBean = new MenuBean();
	}

	@Test
	public void shouldReturnCandidateSearchUrl() {
		final String url = this.menuBean.gotoCandidates();
		assertEquals(CANDIDATE_SEARCH.getUrl() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void shouldReturnClientSearchUrl() {
		final String url = this.menuBean.gotoClients();
		assertEquals(CLIENT_SEARCH.getUrl() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void shouldReturnIndexUrl() {
		final String url = this.menuBean.gotoIndex();
		assertEquals(INDEX.getUrl() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void shouldReturnJobOfferSearchUrl() {
		final String url = this.menuBean.gotoJobOffers();
		assertEquals(JOB_OFFER_SEARCH.getUrl() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void shouldReturnMaintenanceUrl() {
		final String url = this.menuBean.gotoMaintenances();
		assertEquals(MAINTENANCE.getUrl() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void shouldReturnReportsUrl() {
		final String url = this.menuBean.gotoReports();
		assertEquals(REPORT.getUrl() + "?" + FACES_REDIRECT, url);
	}

	@Test
	public void shouldReturnUserSearchUrl() {
		final String url = this.menuBean.gotoUsers();
		assertEquals(USER_SEARCH.getUrl() + "?" + FACES_REDIRECT, url);
	}
}
