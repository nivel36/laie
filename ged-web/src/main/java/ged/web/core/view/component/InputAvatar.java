package ged.web.core.view.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;

import org.primefaces.component.graphicimage.GraphicImage;

@FacesComponent(value = "inputAvatar")
public class InputAvatar extends UIInput implements NamingContainer {

	private GraphicImage image;
	
	private GraphicImage emptyImage;

	public void deleteImage() {
		this.image.setRendered(false);
		this.emptyImage.setRendered(true);
	}

	public GraphicImage getEmptyImage() {
		return emptyImage;
	}

	public void setEmptyImage(GraphicImage emptyImage) {
		this.emptyImage = emptyImage;
	}

	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}

	public GraphicImage getImage() {
		return this.image;
	}

	public void setImage(final GraphicImage image) {
		this.image = image;
	}

	public boolean showDeleteButton() {
		return !this.image.getValue().equals("/image/");
	}
}