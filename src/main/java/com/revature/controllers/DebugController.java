package com.revature.controllers;

import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Comment;
import com.revature.models.Image;
import com.revature.models.Rating;
import com.revature.models.User;
import com.revature.utils.HibernateUtil;

@CrossOrigin
@RestController
public class DebugController {
	@GetMapping("/populate")
	public ResponseEntity<String> populate() {
		try(Session s = HibernateUtil.getSession()){
			Transaction tx = s.beginTransaction();
			//s.save(u);
			
			//List<Image> il1 = new Vector<>();
			//il1.add(i1); il1.add(i2);
			
			User u1 = new User(0, "brian", "whatev", false, false, false);
			User u2 = new User(0, "israel", "password", false, false, true);
			Image i1 = new Image(0, "https://i.imgur.com/26I32vb.jpg", true, "a wittle kitty", u1);
			Image i2 = new Image(0, "https://i.imgur.com/XLfaJkK.jpg", false, "TWO KITTIES!!!!", u1);
			s.save(u1);
			s.save(u2);
			s.save(i1); s.save(i2);
			Comment c1 = new Comment(0, "too few cats", false, u2, i1);
			Comment c2 = new Comment(0, "too many cats", false, u2, i2);
			s.save(c1);
			s.save(c2);
			Rating r1 = new Rating(0, 3, u2, i1);
			Rating r2 = new Rating(0, 2, u2, i2);
			Rating r3 = new Rating(0, 5, u1, i1);
			s.save(r1); s.save(r2); s.save(r3);
			
			tx.commit();
		}
		return new ResponseEntity<String>("done", HttpStatus.OK);
	}
}
