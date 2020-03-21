package com.revature.daos;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.revature.models.Comment;
import com.revature.models.Image;

public interface CommentDAO {
	public List<Comment> getAll();
	public Comment getById(int id);
	public boolean createComment(Comment c);
	public Comment updateComment(Comment c);
	public boolean deleteComment(Comment c);
	public List<Comment> getAllForImage(Image i);
}

