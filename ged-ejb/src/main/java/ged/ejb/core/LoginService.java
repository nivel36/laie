package ged.ejb.core;

import ged.ejb.user.User;

public interface LoginService {

	User login(String email);

	User login(User user);

}