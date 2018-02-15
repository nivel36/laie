package ged.web.core.util;

public enum Page {

	Candidate("/faces/candidate/candidate?id="),
	CandidateSearch("/faces/candidate/candidateSearch?faces-redirect=true"),
	Client("/faces/client/client?id="),
	ClientSearch("/faces/client/clientSearch?faces-redirect=true"),
	Index("/faces/index?faces-redirect=true"),
	JobOffer("/faces/jobOffer/jobOffer?id="),
	JobOfferSearch("/faces/jobOffer/jobOfferSearch?faces-redirect=true"),
	Login("/login?faces-redirect=true"),
	User("/faces/user/user?id="),
	UserSearch("/faces/user/userSearch?faces-redirect=true"),
	Reports("/faces/report/reportSearch?faces-redirect=true"),
	Maintenances("/faces/maintenance/maintenanceSearch?faces-redirect=true");

	private String url;

	Page(final String url) {
		this.url = url;
	}

	public String url() {
		return this.url;
	}
}