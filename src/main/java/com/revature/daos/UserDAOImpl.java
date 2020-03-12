package com.revature.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.revature.models.User;
import com.revature.utils.HibernateUtil;

public class UserDAOImpl implements UserDAO {

	@Override
	public List<User> getAll() {
		try (Session s = HibernateUtil.getSession()) {
			String hql = "from User";
			Query<User> userQuery = s.createQuery(hql, User.class);
			List<User> users = userQuery.list();
			return users;
		}
	}

	@Override
	public User getById(int id) {
		try (Session s = HibernateUtil.getSession()) {
			String hql = "from User where id = :id";
			Query<User> userQuery = s.createQuery(hql, User.class);
			userQuery.setParameter("id", id);
			User user = userQuery.getSingleResult();
			return user;
		}
	}

	@Override
	public User getByUsername(String username) {
		try (Session s = HibernateUtil.getSession()) {
			String hql = "from User where username = :username";
			Query<User> userQuery = s.createQuery(hql, User.class);
			userQuery.setParameter("username", username);
			User user = userQuery.getSingleResult();
			return user;
		}
	}

	@Override
	public User getByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
