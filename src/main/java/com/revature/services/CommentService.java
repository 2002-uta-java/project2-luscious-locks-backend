package com.revature.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.daos.CommentDAO;
import com.revature.daos.CommentDAOImpl;
import com.revature.models.Comment;

@Service
public class CommentService {
	
	private CommentDAO commentDAO = new CommentDAOImpl();
	
	public List<Comment> getAll() {
		return commentDAO.getAll();
	}
	
	public Comment getById(int id) {
		return commentDAO.getById(id);
	}
	
	public boolean createComment(Comment c) {
		return commentDAO.createComment(c);
	}
	
	public Comment updateComment(Comment c) {
		return commentDAO.updateComment(c);
	}
	
	public boolean deleteComment(Comment c) {
		return commentDAO.deleteComment(c);
	}
}
