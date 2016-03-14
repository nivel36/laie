package ged.web.view;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.xml.bind.DatatypeConverter;

import ged.ejb.user.User;
import ged.web.core.view.SessionBean;

public class PasswordValidator implements Validator {

	protected SessionBean getSessionBean(final FacesContext context) {
		return context.getApplication().evaluateExpressionGet(context, "#{sessionBean}", SessionBean.class);
	}

	private String hashPassword(final String plainPassword) {
		String output = null;
		try {
			final MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(plainPassword.getBytes("UTF-8"));
			final byte[] digest = md.digest();
			output = DatatypeConverter.printBase64Binary(digest);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			// TODO: Faces Message
			ex.printStackTrace();
		}
		return output;
	}

	@Override
	public void validate(final FacesContext context, final UIComponent component, final Object value)
			throws ValidatorException {
		final SessionBean sessionBean = getSessionBean(context);
		final User user = sessionBean.getUser();
		final String hashedPassword = hashPassword((String) value);
		if (!hashedPassword.equals(user.getPassword())) {

		}
	}

}
