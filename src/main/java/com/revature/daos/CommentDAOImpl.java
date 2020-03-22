package com.revature.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.models.Comment;
import com.revature.models.Image;
import com.revature.utils.HibernateUtil;

public class CommentDAOImpl implements CommentDAO {

	@Override
	public List<Comment> getAll() {
		try (Session s = HibernateUtil.getSession()) {
			String hql = "from Comment";
			Query<Comment> commentQuery = s.createQuery(hql, Comment.class);
			return commentQuery.list();
		}
	}

	@Override
	public List<Comment> getAllForImage(Image i) {
		try (Session s = HibernateUtil.getSession()) {
			String hql = "from Comment where image = :image";
			Query<Comment> commentQuery = s.createQuery(hql, Comment.class);
			commentQuery.setParameter("image", i);
			return commentQuery.list();
		}
	}

	@Override
	public Comment getById(int id) {
		try (Session s = HibernateUtil.getSession()) {
			String hql = "from Comment where id = :id";
			Query<Comment> commentQuery = s.createQuery(hql, Comment.class);
			commentQuery.setParameter("id", id);
			List<Comment> comments = commentQuery.list();
			if(comments.isEmpty()) {
				return null;
			}
			return comments.get(0);
		}
	}

	@Override
	public boolean createComment(Comment c) {
		try(Session s = HibernateUtil.getSession()){
			Transaction tx = s.beginTransaction();
			s.save(c);
			tx.commit();
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Comment updateComment(Comment c) {
		Comment oldComment = getById(c.getId());
		if(c.getText() != null) {
			oldComment.setText(c.getText());
		}
		if(c.getFlagged() != null) {
			oldComment.setFlagged(c.getFlagged());
		}
		try(Session s = HibernateUtil.getSession()) {
			Transaction tx = s.beginTransaction();
			Comment updatedComment = (Comment) s.merge(oldComment);
			tx.commit();
			return updatedComment;
		}
	}

	@Override
	public boolean deleteComment(Comment c) {
		try(Session s = HibernateUtil.getSession()) {
			Transaction tx = s.beginTransaction();
			s.delete(c);
			tx.commit();
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}
}
