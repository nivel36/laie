package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class CandidateSearchPopupBean extends AbstractCandidateSearchBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -8526606627082697013L;

	protected boolean rendered = false;

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
		logger.debug("CandidateSearchPopupBean init");
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
