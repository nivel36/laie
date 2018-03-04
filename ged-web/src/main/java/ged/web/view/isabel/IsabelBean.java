package ged.web.view.isabel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import ged.web.core.view.AbstractBean;

/**
 * @author Isabel
 *
 */
@Named
@ViewScoped
public class IsabelBean extends AbstractBean {

	private static final long serialVersionUID = 1L;

	private DualListModel<String> campos;
	
	@PostConstruct
	public void init() {
		final List<String> source = new ArrayList<String>();
		source.add("Campo1");
		source.add("Campo2");
		source.add("Campo3 df weir ksdfh iweyr kshdf iweyr skdhf");
		final List<String> target = new ArrayList<String>();
		this.campos = new DualListModel<>(source, target);
	}

	public DualListModel<String> getCampos() {
		return campos;
	}

	public void setCampos(DualListModel<String> campos) {
		this.campos = campos;
	}
}
