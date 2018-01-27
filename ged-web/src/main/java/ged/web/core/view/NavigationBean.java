package ged.web.core.view;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class NavigationBean implements Serializable {

	private static final long serialVersionUID = -6715311084143713699L;

	@Inject
	private FacesContext facesContext;

	public void restoreView(final Object savedState) {
		final UIViewRoot viewRoot = new UIViewRoot();
		viewRoot.restoreState(this.facesContext, savedState);
	}

	public Object saveView() {
		final UIViewRoot viewRoot = this.facesContext.getViewRoot();
		return viewRoot.saveState(this.facesContext);
	}
}
