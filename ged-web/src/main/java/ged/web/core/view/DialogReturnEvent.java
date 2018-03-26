package ged.web.core.view;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;

import org.primefaces.event.AbstractAjaxBehaviorEvent;

public class DialogReturnEvent extends AbstractAjaxBehaviorEvent {

	private static final long serialVersionUID = -6219705795866851848L;

	public DialogReturnEvent(final UIComponent component, final Behavior behavior) {
		super(component, behavior);
	}
}
