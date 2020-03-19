package com.revature.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
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
			for(User u: users) {
				u.setImages(null);
			}
			return users;
		}
	}

	@Override
	public User getById(int id) {
		try (Session s = HibernateUtil.getSession()) {
			String hql = "from User where id = :id";
			Query<User> userQuery = s.createQuery(hql, User.class);
			userQuery.setParameter("id", id);
			//User user = userQuery.getSingleResult();
			List<User> users = userQuery.list();
			if(users.size() == 0) {
				return null;
			}
			users.get(0).getImages().size();
			return users.get(0);
		}
	}

	@Override
	public User getByUsername(String username) {
		try (Session s = HibernateUtil.getSession()) {
			String hql = "from User where username = :username";
			Query<User> userQuery = s.createQuery(hql, User.class);
			userQuery.setParameter("username", username);
			List<User> users = userQuery.list();
			if(users.size() == 0) {
				return null;
			}
			users.get(0).getImages().size();
			return users.get(0);
		}
	}

	@Override
	public User getByUsernameAndPassword(String username, String password) {
		try (Session s = HibernateUtil.getSession()) {
			String hql = "from User where username = :username and password = :password";
			Query<User> userQuery = s.createQuery(hql, User.class);
			userQuery.setParameter("username", username);
			userQuery.setParameter("password", password);
			List<User> users = userQuery.list();
			if(users.size() == 0) {
				return null;
			}
			users.get(0).getImages().size();
			return users.get(0);
		}
	}

	@Override
	public boolean createUser(User u) {
		try(Session s = HibernateUtil.getSession()){
			Transaction tx = s.beginTransaction();
			s.save(u);
			tx.commit();
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public User updateUser(User u) {
		User oldUser = getById(u.getId());
		if(u.getBanned() != null) {
			oldUser.setBanned(u.getBanned());
		}
		if(u.getMuted() != null) {
			oldUser.setMuted(u.getMuted());
		}
		if(u.getUsername() != null) {
			oldUser.setUsername(u.getUsername());
		}
		if(u.getPassword() != null) {
			oldUser.setPassword(u.getPassword());
		}
		try(Session s = HibernateUtil.getSession()){
			Transaction tx = s.beginTransaction();
			User updatedUser = (User) s.merge(oldUser);
			tx.commit();
			return updatedUser;
		}
	}

}
