package ged.web.core.view.component;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;

import org.primefaces.event.SelectEvent;

import ged.ejb.user.User;

@FacesComponent(value = "inputUsers")
public class InputUsers extends UIInput implements NamingContainer {

	private UIInput input;

	@SuppressWarnings("unchecked")
	private void addValueToList(final User user) {
		Object value = this.input.getValue();
		if (value == null) {
			value = new ArrayList<Object>();
		}
		((List<Object>) value).add(user);
	}

	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}

	public UIInput getInput() {
		return this.input;
	}

	public void onDialogReturn(final SelectEvent<User> event) {
		final User user = event.getObject();
		this.addValueToList(user);
	}

	public void setInput(final UIInput input) {
		this.input = input;
	}
}