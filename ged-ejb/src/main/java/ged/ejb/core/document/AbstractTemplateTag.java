package ged.ejb.core.document;

public abstract class AbstractTemplateTag implements TemplateTag {

	protected static final String TAG_DELIMITER = "%";

	public abstract String getTagName();

	@Override
	public String getValue() {
		return TAG_DELIMITER + this.getTagName() + TAG_DELIMITER;
	}

}
