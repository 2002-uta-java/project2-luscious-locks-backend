package com.revature.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.revature.daos.UserDAO;
import com.revature.daos.UserDAOImpl;
import com.revature.models.User;

@Service
public class UserService {
	private UserDAO userDAO = new UserDAOImpl();

	public List<User> getAll() {
		return userDAO.getAll().stream().map(u -> {u.setPassword(null); return u;}).collect(Collectors.toList());
	}

	public User getByUsername(String username) {
		User u = userDAO.getByUsername(username);
		// TODO handle unfound users
		u.setPassword(null);
		return u;
	}

	public User getById(int id) {
		User u = userDAO.getById(id);
		// TODO handle case of user not found
		u.setPassword(null);
		return u;
	}
}
