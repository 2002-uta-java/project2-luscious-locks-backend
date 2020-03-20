package com.revature.auth;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.revature.models.User;
import com.revature.services.UserService;

@Component
@Aspect
public class AuthorizationAspect {

	Logger logger = LoggerFactory.getLogger(AuthorizationAspect.class);
	@Autowired
	UserService userService;
	
	@Before("within(com.revature.controllers.*)")
	public void logRequest(JoinPoint jp) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes()).getRequest();
		logger.info(request.getMethod() + " " + request.getRequestURI());
		logger.info(jp.getSignature().toLongString());
	}

	//@Around("within(com.revature.controllers.*) && !execution(* com.revature.controllers.UserController.populate())")
	//@Around("within(com.revature.controllers.*) && !within(com.revature.controllers.DebugController.*)")
	//@Around("within(com.revature.controllers.*) && !within(com.revature.controller.UserController.populate)")
	@Around("within(com.revature.controllers.*) && !execution(* com.revature.controllers.DebugController.populate())"
			+ " && !execution(* com.revature.controllers.UserController.createUser(..))"
			+ " && !execution(* com.revature.controllers.UserController.login(..))"
			)
	public Object authorizeRequest(ProceedingJoinPoint jp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes()).getRequest();
		String authHeader = request.getHeader("Authorization");
		ResponseEntity<Object> unauth = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		if (authHeader != null) {
			String parts[] = authHeader.split("\\s+");
			if(parts.length != 2 && "Basic".equals(parts[0])) {
				logger.info("malformed authorization header: " + authHeader);
				return unauth;
			}
			byte[] decoded;
			try{
				decoded = Base64.getDecoder().decode(parts[1]);
			} catch(IllegalArgumentException e) {
				logger.info("invalidly formatted credentials");
				return unauth;
			}
			String[] creds = new String(decoded).split(":");
			logger.info("creds = " + creds);
			if(creds.length != 2) {
				logger.info("username and password not found in credentials");
				return unauth;
			}
			User u = userService.getByUsernameAndPassword(creds[0], creds[1]);
			if(u == null) {
				logger.info("invalid username or password with " + creds[0] + " and " + creds[1]);
				return unauth;
			}
			
			logger.info("successful login");
			return jp.proceed();
		} else {
			logger.info("No authorization header sent");
			return unauth;
		}
	}

}
