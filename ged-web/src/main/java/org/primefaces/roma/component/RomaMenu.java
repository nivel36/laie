package org.primefaces.roma.component;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.primefaces.component.api.Widget;
import org.primefaces.component.menu.AbstractMenu;
import org.primefaces.model.menu.MenuModel;

@ListenerFor(sourceClass = RomaMenu.class, systemEventClass = PostAddToViewEvent.class)
public class RomaMenu extends AbstractMenu implements Widget, ComponentSystemEventListener {
	public enum PropertyKeys {
		model, style, styleClass, widgetVar;

		String toString;

		private PropertyKeys() {
		}

		private PropertyKeys(final String toString) {
			this.toString = toString;
		}

		@Override
		public String toString() {
			return this.toString != null ? this.toString : super.toString();
		}
	}

	public static final String COMPONENT_FAMILY = "org.primefaces.component";
	public static final String COMPONENT_TYPE = "org.primefaces.component.RomaMenu";
	private static final String[] LEGACY_RESOURCES = new String[] { "primefaces.css", "jquery/jquery.js",
			"jquery/jquery-plugins.js", "primefaces.js" };

	private static final String[] MODERN_RESOURCES = new String[] { "components.css", "jquery/jquery.js",
			"jquery/jquery-plugins.js", "core.js" };

	public RomaMenu() {
		this.setRendererType("org.primefaces.component.RomaMenuRenderer");
	}

	@Override
	public String getFamily() {
		return "org.primefaces.component";
	}

	@Override
	public MenuModel getModel() {
		return (MenuModel) this.getStateHelper().eval(PropertyKeys.model, (Object) null);
	}

	public String getStyle() {
		return (String) this.getStateHelper().eval(PropertyKeys.style, (Object) null);
	}

	public String getStyleClass() {
		return (String) this.getStateHelper().eval(PropertyKeys.styleClass, (Object) null);
	}

	public String getWidgetVar() {
		return (String) this.getStateHelper().eval(PropertyKeys.widgetVar, (Object) null);
	}

	@Override
	public void processEvent(final ComponentSystemEvent event) throws AbortProcessingException {
		if (event instanceof PostAddToViewEvent) {
			final FacesContext context = this.getFacesContext();
			final UIViewRoot root = context.getViewRoot();

			boolean isPrimeConfig;
			try {
				isPrimeConfig = Class.forName("org.primefaces.config.PrimeConfiguration") != null;
			} catch (final ClassNotFoundException var11) {
				isPrimeConfig = false;
			}

			final String[] resources = isPrimeConfig ? MODERN_RESOURCES : LEGACY_RESOURCES;
			final String[] var6 = resources;
			final int var7 = resources.length;

			for (int var8 = 0; var8 < var7; ++var8) {
				final String res = var6[var8];
				final UIComponent component = context.getApplication().createComponent("javax.faces.Output");
				if (res.endsWith("css")) {
					component.setRendererType("javax.faces.resource.Stylesheet");
				} else if (res.endsWith("js")) {
					component.setRendererType("javax.faces.resource.Script");
				}

				component.getAttributes().put("library", "primefaces");
				component.getAttributes().put("name", res);
				root.addComponentResource(context, component);
			}
		}
	}

	@Override
	public String resolveWidgetVar() {
		final FacesContext context = this.getFacesContext();
		final String userWidgetVar = (String) this.getAttributes().get("widgetVar");
		return userWidgetVar != null ? userWidgetVar
				: "widget_"
						+ this.getClientId(context).replaceAll("-|" + UINamingContainer.getSeparatorChar(context), "_");
	}

	public void setModel(final MenuModel _model) {
		this.getStateHelper().put(PropertyKeys.model, _model);
	}

	public void setStyle(final String _style) {
		this.getStateHelper().put(PropertyKeys.style, _style);
	}

	public void setStyleClass(final String _styleClass) {
		this.getStateHelper().put(PropertyKeys.styleClass, _styleClass);
	}

	public void setWidgetVar(final String _widgetVar) {
		this.getStateHelper().put(PropertyKeys.widgetVar, _widgetVar);
	}
}