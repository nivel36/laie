package ged.web.core.view.component;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UICommand;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

@FacesComponent(value = "inputClient")
public class InputClient extends UIInput implements NamingContainer {

	private UICommand cleanButton;

	private UIInput input;

	public void clean() {
		this.input.setValue(null);
		hideCleanButton();
	}

	@Override
	public void encodeBegin(final FacesContext context) throws IOException {
		processCleanButton();
		super.encodeBegin(context);
	}

	private void processCleanButton() {
		if (isInputValueSet()) {
			showCleanButton();
		}
		else {
			hideCleanButton();
		}
	}

	private boolean isInputValueSet() {
		return input.getValue() != null;
	}

	private void hideCleanButton() {
		cleanButton.setRendered(false);
	}

	private void showCleanButton() {
		cleanButton.setRendered(true);
	}

	public UICommand getCleanButton() {
		return cleanButton;
	}

	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}

	public UIInput getInput() {
		return input;
	}

	public void setCleanButton(final UICommand cleanButton) {
		this.cleanButton = cleanButton;
	}

	public void setInput(final UIInput input) {
		this.input = input;
	}
}
