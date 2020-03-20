package com.revature.daos;

import java.util.List;

import com.revature.models.Comment;

public interface CommentDAO {
	public List<Comment> getAll();
	public Comment getById(int id);
	public boolean createComment(Comment c);
	public Comment updateComment(Comment c);
	public boolean deleteComment(Comment c);
}

