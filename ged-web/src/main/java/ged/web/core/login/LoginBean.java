package ged.web.core.login;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.user.UserService;

@Named
@RequestScoped
public class LoginBean {
	
	@Inject
	private UserService userService;
		
	private String user;
	
	private String password;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String login(){
		return "index";
	}
	
	public String logout(){
		return "login";		
	}

}
