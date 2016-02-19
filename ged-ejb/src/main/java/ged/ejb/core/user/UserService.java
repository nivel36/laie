package ged.ejb.core.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.GenericServiceImpl;

@Stateless
public class UserService extends GenericServiceImpl {

	@Inject
	private UserDao userDao;
	
	public List<User> findUsers(String name, String surenames) {
		return userDao.findUsers(name, surenames);
	}
	
	public List<User> findAll() {
		return userDao.findAll();
	}

	public boolean checkPassword(String userString, String password) throws NoSuchAlgorithmException {
		User user = userDao.findByName(userString);
		String userPassword = user.getPassword();
		MessageDigest mda = MessageDigest.getInstance("SHA-512");
		byte[] hashPassword = mda.digest(password.getBytes());
		return false;
	}

}
