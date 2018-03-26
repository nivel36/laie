package ged.web.core.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.primefaces.component.dialog.Dialog;
import org.primefaces.event.CloseEvent;

@FacesComponent
public class AfDialog extends UINamingContainer {

	private Dialog dialog;

	public void closeDialog() {
		this.dialog.setVisible(false);
	}

	public Dialog getDialog() {
		return this.dialog;
	}

	public void handleClose(final CloseEvent event) {

	}

	public boolean isVisible() {
		return this.dialog.isVisible();
	}

	public void setDialog(final Dialog dialog) {
		this.dialog = dialog;
	}

	public void showDialog() {
		this.dialog.setVisible(true);
	}
}