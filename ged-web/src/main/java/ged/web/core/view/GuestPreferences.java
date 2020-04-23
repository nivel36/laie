package ged.web.core.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class GuestPreferences implements Serializable {

	private static final String POPUP = "popup";

	private static final long serialVersionUID = 1L;

	private String logo = "logo-roma-white";

	private String menuColor = "layout-menu-dark";

	private String menuMode = "layout-static";

	private String profileMode = POPUP;

	private String theme = "blue";

	private String topBarColor = "layout-topbar-dark";

	public String getLogo() {
		return this.logo;
	}

	public String getMenuColor() {
		return this.menuColor;
	}

	public String getMenuMode() {
		return this.menuMode;
	}

	public String getProfileMode() {
		return this.profileMode;
	}

	public String getTheme() {
		return this.theme;
	}

	public String getTopBarColor() {
		return this.topBarColor;
	}

	public void setMenuColor(final String menuColor) {
		this.menuColor = menuColor;
	}

	public void setMenuMode(final String menuMode) {
		this.menuMode = menuMode;

		if (this.menuMode.equals("layout-horizontal")) {
			this.profileMode = POPUP;
		}
	}

	public void setProfileMode(final String profileMode) {
		if (this.menuMode.equals("layout-horizontal")) {
			this.profileMode = POPUP;
		} else {
			this.profileMode = profileMode;
		}
	}

	public void setTheme(final String theme) {
		this.theme = theme;
	}

	public void setTopBarColor(final String topBarColor, final String logo) {
		this.topBarColor = topBarColor;
		this.logo = logo;
	}
}