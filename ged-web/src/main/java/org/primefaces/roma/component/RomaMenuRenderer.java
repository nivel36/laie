package org.primefaces.roma.component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.api.AjaxSource;
import org.primefaces.component.api.UIOutcomeTarget;
import org.primefaces.component.menu.AbstractMenu;
import org.primefaces.component.menu.BaseMenuRenderer;
import org.primefaces.component.menuitem.UIMenuItem;
import org.primefaces.component.submenu.UISubmenu;
import org.primefaces.expression.SearchExpressionFacade;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.Separator;
import org.primefaces.model.menu.Submenu;
import org.primefaces.util.AjaxRequestBuilder;
import org.primefaces.util.ComponentTraversalUtils;
import org.primefaces.util.WidgetBuilder;

public class RomaMenuRenderer extends BaseMenuRenderer {
	protected String createAjaxRequest(final FacesContext context, final AbstractMenu menu, final AjaxSource source,
			final UIComponent form, final Map<String, List<String>> params) {
		final String clientId = menu.getClientId(context);
		final AjaxRequestBuilder builder = this.getAjaxRequestBuilder();
		builder.init().source(clientId).process(menu, source.getProcess()).update(menu, source.getUpdate())
				.async(source.isAsync()).global(source.isGlobal()).delay(source.getDelay()).timeout(source.getTimeout())
				.partialSubmit(source.isPartialSubmit(), source.isPartialSubmitSet(), source.getPartialSubmitFilter())
				.resetValues(source.isResetValues(), source.isResetValuesSet())
				.ignoreAutoUpdate(source.isIgnoreAutoUpdate()).onstart(source.getOnstart()).onerror(source.getOnerror())
				.onsuccess(source.getOnsuccess()).oncomplete(source.getOncomplete()).params(params);
		if (form != null) {
			builder.form(form.getClientId(context));
		}

		builder.preventDefault();
		return builder.build();
	}

	protected String createAjaxRequest(final FacesContext context, final AjaxSource source, final UIComponent form) {
		final UIComponent component = (UIComponent) source;
		final String clientId = component.getClientId(context);
		final AjaxRequestBuilder builder = this.getAjaxRequestBuilder();
		builder.init().source(clientId)
				.form(SearchExpressionFacade.resolveClientId(context, component, source.getForm()))
				.process(component, source.getProcess()).update(component, source.getUpdate()).async(source.isAsync())
				.global(source.isGlobal()).delay(source.getDelay()).timeout(source.getTimeout())
				.partialSubmit(source.isPartialSubmit(), source.isPartialSubmitSet(), source.getPartialSubmitFilter())
				.resetValues(source.isResetValues(), source.isResetValuesSet())
				.ignoreAutoUpdate(source.isIgnoreAutoUpdate()).onstart(source.getOnstart()).onerror(source.getOnerror())
				.onsuccess(source.getOnsuccess()).oncomplete(source.getOncomplete()).params(component);
		if (form != null) {
			builder.form(form.getClientId(context));
		}

		builder.preventDefault();
		return builder.build();
	}

	protected void encodeBadge(final FacesContext context, final Object value) throws IOException {
		if (value != null) {
			final ResponseWriter writer = context.getResponseWriter();
			writer.startElement("span", (UIComponent) null);
			writer.writeAttribute("class", "menuitem-badge", (String) null);
			writer.writeText(value.toString(), (String) null);
			writer.endElement("span");
		}

	}

	protected void encodeElement(final FacesContext context, final AbstractMenu menu, final MenuElement element)
			throws IOException {
		final ResponseWriter writer = context.getResponseWriter();
		if (element.isRendered()) {
			String submenuClientId;
			String style;
			String styleClass;
			if (element instanceof MenuItem) {
				final MenuItem menuItem = (MenuItem) element;
				submenuClientId = menuItem instanceof UIComponent ? menuItem.getClientId()
						: menu.getClientId(context) + "_" + menuItem.getClientId();
				style = menuItem.getContainerStyle();
				styleClass = menuItem.getContainerStyleClass();
				writer.startElement("li", (UIComponent) null);
				writer.writeAttribute("id", submenuClientId, (String) null);
				writer.writeAttribute("role", "menuitem", (String) null);
				if (style != null) {
					writer.writeAttribute("style", style, (String) null);
				}

				if (styleClass != null) {
					writer.writeAttribute("class", styleClass, (String) null);
				}

				this.encodeMenuItem(context, menu, menuItem);
				writer.endElement("li");
			} else if (element instanceof Submenu) {
				final Submenu submenu = (Submenu) element;
				submenuClientId = submenu instanceof UIComponent ? ((UIComponent) submenu).getClientId()
						: menu.getClientId(context) + "_" + submenu.getId();
				style = submenu.getStyle();
				styleClass = submenu.getStyleClass();
				writer.startElement("li", (UIComponent) null);
				writer.writeAttribute("id", submenuClientId, (String) null);
				writer.writeAttribute("role", "menuitem", (String) null);
				if (style != null) {
					writer.writeAttribute("style", style, (String) null);
				}

				if (styleClass != null) {
					writer.writeAttribute("class", styleClass, (String) null);
				}

				this.encodeSubmenu(context, menu, submenu);
				writer.endElement("li");
			} else if (element instanceof Separator) {
				this.encodeSeparator(context, (Separator) element);
			}
		}

	}

	protected void encodeElements(final FacesContext context, final AbstractMenu menu, final List<MenuElement> elements)
			throws IOException {
		final int size = elements.size();

		for (int i = 0; i < size; ++i) {
			this.encodeElement(context, menu, elements.get(i));
		}

	}

	protected void encodeItemIcon(final FacesContext context, String icon) throws IOException {
		if (icon != null) {
			final ResponseWriter writer = context.getResponseWriter();
			writer.startElement("i", (UIComponent) null);
			if (icon.contains("fa ")) {
				icon = icon + " fa-fw";
			}

			writer.writeAttribute("class", icon + " layout-menuitem-icon", (String) null);
			writer.endElement("i");
		}

	}

	@Override
	protected void encodeMarkup(final FacesContext context, final AbstractMenu abstractMenu) throws IOException {
		final RomaMenu menu = (RomaMenu) abstractMenu;
		final ResponseWriter writer = context.getResponseWriter();
		final String style = menu.getStyle();
		String styleClass = menu.getStyleClass();
		final String defaultStyleClass = "layout-menu";
		styleClass = styleClass == null ? defaultStyleClass : defaultStyleClass + " " + styleClass;
		writer.startElement("ul", menu);
		writer.writeAttribute("id", menu.getClientId(context), "id");
		writer.writeAttribute("class", styleClass, "styleClass");
		if (style != null) {
			writer.writeAttribute("style", style, "style");
		}

		if (menu.getElementsCount() > 0) {
			this.encodeElements(context, menu, menu.getElements());
		}

		writer.endElement("ul");
	}

	@Override
	protected void encodeMenuItem(final FacesContext context, final AbstractMenu menu, final MenuItem menuitem)
			throws IOException {
		final ResponseWriter writer = context.getResponseWriter();
		final String title = menuitem.getTitle();
		final boolean disabled = menuitem.isDisabled();
		final Object value = menuitem.getValue();
		final String style = menuitem.getStyle();
		final String styleClass = menuitem.getStyleClass();
		writer.startElement("a", (UIComponent) null);
		if (title != null) {
			writer.writeAttribute("title", title, (String) null);
		}

		if (style != null) {
			writer.writeAttribute("style", style, (String) null);
		}

		if (styleClass != null) {
			writer.writeAttribute("class", styleClass, (String) null);
		}

		if (disabled) {
			writer.writeAttribute("href", "#", (String) null);
			writer.writeAttribute("onclick", "return false;", (String) null);
		} else {
			String onclick = menuitem.getOnclick();
			if ((menuitem.getUrl() == null) && (menuitem.getOutcome() == null)) {
				writer.writeAttribute("href", "#", (String) null);
				final UIComponent form = ComponentTraversalUtils.closestForm(context, menu);
				if (form == null) {
					throw new FacesException("MenuItem must be inside a form element");
				}

				String command;
				if (menuitem.isDynamic()) {
					final String menuClientId = menu.getClientId(context);
					Map<String, List<String>> params = menuitem.getParams();
					if (params == null) {
						params = new LinkedHashMap<>();
					}

					final List<String> idParams = new ArrayList<>();
					idParams.add(menuitem.getId());
					params.put(menuClientId + "_menuid", idParams);
					command = menuitem.isAjax()
							? this.createAjaxRequest(context, menu, (AjaxSource) menuitem, form, params)
							: this.buildNonAjaxRequest(context, menu, form, menuClientId, params, true);
				} else {
					command = menuitem.isAjax() ? this.createAjaxRequest(context, (AjaxSource) menuitem, form)
							: this.buildNonAjaxRequest(context, (UIComponent) menuitem, form,
									((UIComponent) menuitem).getClientId(context), true);
				}

				onclick = onclick == null ? command : onclick + ";" + command;
			} else {
				final String targetURL = this.getTargetURL(context, (UIOutcomeTarget) menuitem);
				writer.writeAttribute("href", targetURL, (String) null);
				if (menuitem.getTarget() != null) {
					writer.writeAttribute("target", menuitem.getTarget(), (String) null);
				}
			}

			if (onclick != null) {
				writer.writeAttribute("onclick", onclick, (String) null);
			}
		}

		this.encodeMenuItemContent(context, menu, menuitem);
		writer.endElement("a");
		if (value != null) {
			this.encodeTooltip(context, value);
		}

	}

	@Override
	protected void encodeMenuItemContent(final FacesContext context, final AbstractMenu menu, final MenuItem menuitem)
			throws IOException {
		final ResponseWriter writer = context.getResponseWriter();
		final String icon = menuitem.getIcon();
		final Object value = menuitem.getValue();
		if (menuitem instanceof UIMenuItem) {
			this.encodeBadge(context, ((UIMenuItem) menuitem).getAttributes().get("badge"));
		}

		this.encodeItemIcon(context, icon);
		if (value != null) {
			writer.startElement("span", (UIComponent) null);
			writer.writeAttribute("class", "layout-menuitem-text", (String) null);
			writer.writeText(value, "value");
			writer.endElement("span");
		}

	}

	@Override
	protected void encodeScript(final FacesContext context, final AbstractMenu abstractMenu) throws IOException {
		final RomaMenu menu = (RomaMenu) abstractMenu;
		final String clientId = menu.getClientId(context);
		final WidgetBuilder wb = this.getWidgetBuilder(context);
		wb.init("Roma", menu.resolveWidgetVar(), clientId).finish();
	}

	@Override
	protected void encodeSeparator(final FacesContext context, final Separator separator) throws IOException {
		final ResponseWriter writer = context.getResponseWriter();
		final String style = separator.getStyle();
		String styleClass = separator.getStyleClass();
		styleClass = styleClass == null ? "Separator" : "Separator " + styleClass;
		writer.startElement("li", (UIComponent) null);
		writer.writeAttribute("class", styleClass, (String) null);
		if (style != null) {
			writer.writeAttribute("style", style, (String) null);
		}

		writer.endElement("li");
	}

	protected void encodeSubmenu(final FacesContext context, final AbstractMenu menu, final Submenu submenu)
			throws IOException {
		final ResponseWriter writer = context.getResponseWriter();
		final String icon = submenu.getIcon();
		final String label = submenu.getLabel();
		final int childrenElementsCount = submenu.getElementsCount();
		writer.startElement("a", (UIComponent) null);
		writer.writeAttribute("href", "#", (String) null);
		this.encodeItemIcon(context, icon);
		if (label != null) {
			writer.startElement("span", (UIComponent) null);
			writer.writeAttribute("class", "layout-menuitem-text", (String) null);
			writer.writeText(label, (String) null);
			writer.endElement("span");
			this.encodeToggleIcon(context, submenu, childrenElementsCount);
			if (submenu instanceof UISubmenu) {
				this.encodeBadge(context, ((UISubmenu) submenu).getAttributes().get("badge"));
			}
		}

		writer.endElement("a");
		if (label != null) {
			this.encodeTooltip(context, label);
		}

		if (childrenElementsCount > 0) {
			writer.startElement("ul", (UIComponent) null);
			writer.writeAttribute("role", "menu", (String) null);
			this.encodeElements(context, menu, submenu.getElements());
			writer.endElement("ul");
		}

	}

	protected void encodeToggleIcon(final FacesContext context, final Submenu submenu, final int childrenElementsCount)
			throws IOException {
		if (childrenElementsCount > 0) {
			final ResponseWriter writer = context.getResponseWriter();
			writer.startElement("i", (UIComponent) null);
			writer.writeAttribute("class", "fa fa-fw layout-submenu-toggler fa-angle-down", (String) null);
			writer.endElement("i");
		}

	}

	protected void encodeTooltip(final FacesContext context, final Object value) throws IOException {
		final ResponseWriter writer = context.getResponseWriter();
		writer.startElement("div", (UIComponent) null);
		writer.writeAttribute("class", "layout-menu-tooltip", (String) null);
		writer.startElement("div", (UIComponent) null);
		writer.writeAttribute("class", "layout-menu-tooltip-arrow", (String) null);
		writer.endElement("div");
		writer.startElement("div", (UIComponent) null);
		writer.writeAttribute("class", "layout-menu-tooltip-text", (String) null);
		writer.writeText(value, (String) null);
		writer.endElement("div");
		writer.endElement("div");
	}

	protected AjaxRequestBuilder getAjaxRequestBuilder() {
		Class rootContext;
		try {
			rootContext = Class.forName("org.primefaces.context.PrimeRequestContext");
		} catch (final ClassNotFoundException var8) {
			try {
				rootContext = Class.forName("org.primefaces.context.RequestContext");
			} catch (final ClassNotFoundException var7) {
				throw new IllegalStateException(var7);
			}
		}

		try {
			Method method = rootContext.getMethod("getCurrentInstance");
			final Object requestContextInstance = method.invoke((Object) null);
			method = requestContextInstance.getClass().getMethod("getAjaxRequestBuilder");
			return (AjaxRequestBuilder) method.invoke(requestContextInstance);
		} catch (final Exception var6) {
			throw new IllegalStateException(var6);
		}
	}
}