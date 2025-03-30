package es.nivel36.laie.web.core.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.PrimeFaces;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class GuestPreferences implements Serializable {


	private static final long serialVersionUID = 842996720459089979L;

	private String menuMode = "layout-slim";
	private String componentTheme = "blue";
	private String menuColor = "light";
	private String topbarColor = "blue";
	private boolean lightLogo = true;
	private String profileMode = "popup";
	private boolean orientationRTL;
	private String inputStyle = "outlined";
	private List<TopbarColor> topbarColors = new ArrayList<>();
	private List<ComponentTheme> componentThemes = new ArrayList<>();

	@PostConstruct
	public void init() {
		topbarColors.add(new TopbarColor("Light", "light", "#ffffff"));
		topbarColors.add(new TopbarColor("Dark", "dark", "#252529"));
		topbarColors.add(new TopbarColor("Blue", "blue", "#0772B3"));
		topbarColors.add(new TopbarColor("Green", "green", "#0F8C50"));
		topbarColors.add(new TopbarColor("Orange", "orange", "#C76D09"));
		topbarColors.add(new TopbarColor("Magenta", "magenta", "#972BB1"));
		topbarColors.add(new TopbarColor("Blue Grey", "bluegrey", "#406E7E"));
		topbarColors.add(new TopbarColor("Deep Purple", "deeppurple", "#543CD9"));
		topbarColors.add(new TopbarColor("Brown", "brown", "#794F36"));
		topbarColors.add(new TopbarColor("Lime", "lime", "#849201"));
		topbarColors.add(new TopbarColor("Rose", "rose", "#8F3939"));
		topbarColors.add(new TopbarColor("Cyan", "cyan", "#0C8990"));
		topbarColors.add(new TopbarColor("Teal", "teal", "#337E59"));
		topbarColors.add(new TopbarColor("Deep Orange", "deeporange", "#D74A1D"));
		topbarColors.add(new TopbarColor("Indigo", "indigo", "#3D53C9"));
		topbarColors.add(new TopbarColor("Pink", "pink", "#BF275B"));
		topbarColors.add(new TopbarColor("Purple", "purple", "#7F32DA"));

		componentThemes.add(new ComponentTheme("Blue", "blue", "#0f97c7"));
		componentThemes.add(new ComponentTheme("Blue Grey", "bluegrey", "#578697"));
		componentThemes.add(new ComponentTheme("Brown", "brown", "#97664A"));
		componentThemes.add(new ComponentTheme("Cyan", "cyan", "#1BA7AF"));
		componentThemes.add(new ComponentTheme("Deep Orange", "deeporange", "#F96F43"));
		componentThemes.add(new ComponentTheme("Deep Purple", "deeppurple", "#6952EC"));
		componentThemes.add(new ComponentTheme("Green", "green", "#10B163"));
		componentThemes.add(new ComponentTheme("Teal", "teal", "#4EA279"));
		componentThemes.add(new ComponentTheme("Indigo", "indigo", "#435AD8"));
		componentThemes.add(new ComponentTheme("Lime", "lime", "#A5B600"));
		componentThemes.add(new ComponentTheme("Magenta", "magenta", "#B944D6"));
		componentThemes.add(new ComponentTheme("Orange", "orange", "#E2841A"));
		componentThemes.add(new ComponentTheme("Pink", "pink", "#E93A76"));
		componentThemes.add(new ComponentTheme("Purple", "purple", "#9643F9"));
		componentThemes.add(new ComponentTheme("Rose", "rose", "#AB5353"));

	}

	public String getMenuMode() {
		return this.menuMode;
	}

	public void setMenuMode(String menuMode) {
		this.menuMode = menuMode;

		if (this.menuMode.equals("layout-horizontal") && this.profileMode.equals("inline")) {
			this.profileMode = "popup";
			PrimeFaces.current().executeScript("location.reload();");
		}
	}

	public String getMenuColor() {
		return this.menuColor;
	}

	public void setMenuColor(String menuColor) {
		this.menuColor = menuColor;
	}

	public String getTopbarColor() {
		return this.topbarColor;
	}

	public void setTopbarColor(String topbarColor) {
		this.topbarColor = topbarColor;
		this.lightLogo = !this.topbarColor.equals("light");
	}

	public boolean isLightLogo() {
		return lightLogo;
	}

	public String getProfileMode() {
		return this.profileMode;
	}

	public void setProfileMode(String profileMode) {
		if (this.menuMode.equals("layout-horizontal")) {
			this.profileMode = "popup";
		} else {
			this.profileMode = profileMode;
		}
	}

	public boolean isOrientationRTL() {
		return orientationRTL;
	}

	public void setOrientationRTL(boolean orientationRTL) {
		this.orientationRTL = orientationRTL;
	}

	public String getComponentTheme() {
		return componentTheme;
	}

	public void setComponentTheme(String componentTheme) {
		this.componentTheme = componentTheme;
	}

	public String getInputStyle() {
		return inputStyle;
	}

	public String getInputStyleClass() {
		return this.inputStyle.equals("filled") ? "ui-input-filled" : "";
	}

	public void setInputStyle(String inputStyle) {
		this.inputStyle = inputStyle;
	}

	public List<TopbarColor> getTopbarColors() {
		return topbarColors;
	}

	public List<ComponentTheme> getComponentThemes() {
		return componentThemes;
	}

	public class TopbarColor {
		String name;
		String file;
		String color;

		public TopbarColor(String name, String file, String color) {
			this.name = name;
			this.file = file;
			this.color = color;
		}

		public String getName() {
			return this.name;
		}

		public String getFile() {
			return this.file;
		}

		public String getColor() {
			return this.color;
		}
	}

	public class ComponentTheme {
		String name;
		String file;
		String color;

		public ComponentTheme(String name, String file, String color) {
			this.name = name;
			this.file = file;
			this.color = color;
		}

		public String getName() {
			return this.name;
		}

		public String getFile() {
			return this.file;
		}

		public String getColor() {
			return this.color;
		}
	}
}
