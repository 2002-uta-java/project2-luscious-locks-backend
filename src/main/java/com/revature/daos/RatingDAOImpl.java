package com.revature.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.models.Image;
import com.revature.models.Rating;
import com.revature.utils.HibernateUtil;

public class RatingDAOImpl implements RatingDAO {

	@Override
	public List<Rating> getAllForImage(Image i) {
		try (Session s = HibernateUtil.getSession()) {
			String hql = "from Rating where image = :image";
			Query<Rating> ratingQuery = s.createQuery(hql, Rating.class);
			ratingQuery.setParameter("image", i);
			return ratingQuery.list();
		}
	}

	@Override
	public Rating getById(int id) {
		try (Session s = HibernateUtil.getSession()) {
			String hql = "from Rating where id = :id";
			Query<Rating> ratingQuery = s.createQuery(hql, Rating.class);
			ratingQuery.setParameter("id", id);
			List<Rating> ratings = ratingQuery.list();
			if (ratings.isEmpty()) {
				return null;
			}
			return ratings.get(0);
		}
	}

	@Override
	public boolean createRating(Rating r) {
		try (Session s = HibernateUtil.getSession()) {
			Transaction tx = s.beginTransaction();
			s.save(r);
			tx.commit();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteRating(Rating r) {
		try (Session s = HibernateUtil.getSession()) {
			Transaction tx = s.beginTransaction();
			s.delete(r);
			tx.commit();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Rating updateRating(Rating r) {
		Rating oldRating = getById(r.getId());
		oldRating.setRating(r.getRating());
		try (Session s = HibernateUtil.getSession()) {
			Transaction tx = s.beginTransaction();
			Rating updatedRating = (Rating) s.merge(oldRating);
			tx.commit();
			return updatedRating;
		}
	}

}
