package ged.web.core.view;

import org.primefaces.PrimeFaces;

public abstract class AbstractDialogBean extends AbstractBean {

	private static final long serialVersionUID = 8427233134254733395L;

	public void closeDialog() {
		this.closeDialog(null);
	}

	protected void closeDialog(final Object value) {
		PrimeFaces.current().dialog().closeDynamic(value);
	}

}