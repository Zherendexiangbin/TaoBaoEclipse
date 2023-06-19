package service;

import dao.UserDao;
import entity.User;

public class UserService {

	public static boolean CheckLogin(User user) {
		User u = UserDao.getOne(user.getName());
		return u != null && u.getPassword().equals(user.getPassword());
	}

}
