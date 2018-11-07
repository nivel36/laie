package ged.web.core.view.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.event.ActionEvent;

import org.primefaces.component.commandbutton.CommandButton;

@FacesComponent(value = "inputSearch")
public class InputSearch extends UIInput implements NamingContainer {

	private CommandButton searchCommandButton;

	private UIInput searchInputText;

	public void clean() {
		this.searchInputText.setValue(null);
		final ActionEvent action = new ActionEvent(searchCommandButton);
		action.queue();
	}

	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}

	public CommandButton getSearchCommandButton() {
		return searchCommandButton;
	}

	public UIInput getSearchInputText() {
		return searchInputText;
	}

	public void setSearchCommandButton(final CommandButton searchCommandButton) {
		this.searchCommandButton = searchCommandButton;
	}

	public void setSearchInputText(final UIInput searchInputText) {
		this.searchInputText = searchInputText;
	}
}
