package com.revature.controllers;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.revature.models.Comment;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.test.config.TestBeanConfig;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class})
public class CommentControllerTest {
	
	@Autowired
	private CommentController commentController;
	@Autowired
	private UserService userService;

	@Test
	public void createCommentTest() {
		HttpSession session = new MockHttpSession();
		User u = userService.getById(1);
		session.setAttribute("user", u);
		Comment c = new Comment();
		c.setText("comment");
		ResponseEntity<String> result = commentController.createComment(c, 1, session);
		assertNotNull(result);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		result = commentController.createComment(new Comment(), 0, session);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
}