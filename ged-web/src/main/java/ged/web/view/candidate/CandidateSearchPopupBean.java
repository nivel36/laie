package ged.web.view.candidate;

import java.util.logging.Logger;

import javax.inject.Inject;

import ged.ejb.candidate.CandidateService;

public class CandidateSearchPopupBean extends AbstractCandidateSearchBean {

	public static final transient Logger logger = Logger.getLogger(CandidateSearchPopupBean.class.getName());

	private static final long serialVersionUID = -8526606627082697013L;

	protected boolean rendered = false;

	@Inject
	public CandidateSearchPopupBean(final CandidateService candidateService) {
		super(candidateService);
	}

	public void cancel() {
		hide();
	}

	public void hide() {
		clean();
		this.rendered = false;
	}

	public boolean isRendered() {
		return this.rendered;
	}

	public void open() {
		this.rendered = true;
	}

	public void show() {
		this.rendered = true;
		search();
	}
}
