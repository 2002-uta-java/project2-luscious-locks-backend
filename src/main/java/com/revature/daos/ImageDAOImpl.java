package com.revature.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.models.Image;
import com.revature.models.User;
import com.revature.utils.HibernateUtil;

public class ImageDAOImpl implements ImageDAO{

	@Override
	public List<Image> getAll() {
		try(Session s = HibernateUtil.getSession()){
			String hql = "from Image";
			Query<Image> imageQuery = s.createQuery(hql, Image.class);
			return imageQuery.list();
		}
	}
	
	@Override
	public List<Image> getAtOrAvoceRating(float rating) {
		try(Session s = HibernateUtil.getSession()){
			String sql = "select i.* from image i join rating r on i.id = r.image_id group by i.id having avg(r.rating) >= ?;";
			Query<Image> imageQuery = s.createNativeQuery(sql, Image.class);
			imageQuery.setParameter(1, rating);
			return imageQuery.list();
		}
	}
	
	@Override
	public Image getById(int id) {
		try(Session s = HibernateUtil.getSession()){
			String hql = "from Image where id = :id";
			Query<Image> imageQuery = s.createQuery(hql, Image.class);
			imageQuery.setParameter("id", id);
			return imageQuery.getSingleResult();
		}
	}

	@Override
	public List<Image> getForUser(User u) {
		try(Session s = HibernateUtil.getSession()){
			String hql = "from Image where poster = :poster";
			Query<Image> imageQuery = s.createQuery(hql, Image.class);
			imageQuery.setParameter("poster", u);
			return imageQuery.list();
		}
	}
	
	@Override
	public boolean createImage(Image i) {
		try(Session s = HibernateUtil.getSession()){
			Transaction tx = s.beginTransaction();
			s.save(i);
			tx.commit();
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Image updateImage(Image i) {
		try(Session s = HibernateUtil.getSession()){
			Transaction tx = s.beginTransaction();
			Image updatedImage = (Image) s.merge(i);
			tx.commit();
			return updatedImage;
		}
	}

	@Override
	public boolean deleteImage(Image i) {
		try(Session s = HibernateUtil.getSession()){
			Transaction tx = s.beginTransaction();
			s.delete(i);
			tx.commit();
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
}
