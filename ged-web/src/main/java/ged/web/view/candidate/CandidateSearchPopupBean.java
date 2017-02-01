package ged.web.view.candidate;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.CandidateService;
import ged.web.core.view.Paginator;

@Named
@ViewScoped
public class CandidateSearchPopupBean extends AbstractCandidateSearchBean {

	public static final transient Logger logger = LoggerFactory.getLogger(CandidateSearchPopupBean.class.getName());

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

	@Override
	@PostConstruct
	public void init() {
		this.paginator = new Paginator<>(this.sessionBean.getRowsPerPage());
		if (isRendered()) {
			search();
		}
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
