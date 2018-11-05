package ged.web.core.view.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;

@FacesComponent(value = "inputClient")
public class InputClient extends UIInput implements NamingContainer {

	private UIInput input;

	public void clean() {
		this.input.setValue(null);
	}

	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}

	public UIInput getInput() {
		return input;
	}

	public void setInput(final UIInput input) {
		this.input = input;
	}
}
