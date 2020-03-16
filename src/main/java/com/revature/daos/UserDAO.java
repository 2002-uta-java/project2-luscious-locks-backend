package com.revature.daos;

import java.util.List;

import com.revature.models.User;

public interface UserDAO {
	public List<User> getAll();
	public User getById(int id);
	public User getByUsername(String username);
	public User getByUsernameAndPassword(String username, String password);
}
