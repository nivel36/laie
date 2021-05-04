package ged.web.core.view.component;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UICommand;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

import org.primefaces.component.graphicimage.GraphicImage;

@FacesComponent(value = "inputAvatar")
public class InputAvatar extends UIInput implements NamingContainer {

	private static final String IMAGES = "/images/";
	
	private static final String NO_IMAGE = "/resources/img/default-avatar.png";

	private UICommand deleteButton;

	private GraphicImage image;

	public void deleteImage() {
		this.image.setValue("");
		this.image.setRendered(false);
	}
	
	@Override
	public void encodeBegin(final FacesContext context) throws IOException {
		super.encodeBegin(context);
	}

	public UICommand getDeleteButton() {
		return deleteButton;
	}

	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}

	public GraphicImage getImage() {
		return this.image;
	}

	public void setDeleteButton(UICommand deleteButton) {
		this.deleteButton = deleteButton;
	}

	public void setImage(final GraphicImage image) {
		this.image = image;
	}
}