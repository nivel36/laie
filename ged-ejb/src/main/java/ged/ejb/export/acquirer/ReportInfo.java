package ged.ejb.export.acquirer;

/**
 * @author Isabel
 *
 */
public final class ReportInfo {

	private final long id;
	
	private final String name;

	public ReportInfo(final long id, final String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
