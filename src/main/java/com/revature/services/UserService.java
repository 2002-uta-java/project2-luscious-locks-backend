package com.revature.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.daos.UserDAO;
import com.revature.daos.UserDAOImpl;
import com.revature.models.User;

@Service
public class UserService {
	private UserDAO userDAO = new UserDAOImpl();

	public List<User> getAll() {
		return userDAO.getAll();
	}

	public User getByUsername(String username) {
		return userDAO.getByUsername(username);
	}

	public User getById(int id) {
		return userDAO.getById(id);
	}
}
