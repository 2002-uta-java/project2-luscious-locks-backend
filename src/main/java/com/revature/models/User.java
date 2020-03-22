package com.revature.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name="\"user\"")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable=false, unique=true)
	private String username;
	private String password;
	private Boolean muted;
	private Boolean banned;
	private Boolean moderator;
	private String warning;
	
	public User() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return null;
	}
	public String getActualPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getMuted() {
		return muted;
	}
	public void setMuted(Boolean muted) {
		this.muted = muted;
	}
	public Boolean getBanned() {
		return banned;
	}
	public void setBanned(Boolean banned) {
		this.banned = banned;
	}
	
	public Boolean getModerator() {
		return moderator;
	}
	public void setModerator(Boolean moderator) {
		this.moderator = moderator;
	}
	public String getWarning() {
		return warning;
	}
	public void setWarning(String warning) {
		this.warning = warning;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((banned == null) ? 0 : banned.hashCode());
		result = prime * result + id;
		result = prime * result + ((moderator == null) ? 0 : moderator.hashCode());
		result = prime * result + ((muted == null) ? 0 : muted.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((warning == null) ? 0 : warning.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (banned == null) {
			if (other.banned != null)
				return false;
		} else if (!banned.equals(other.banned))
			return false;
		if (id != other.id)
			return false;
		if (moderator == null) {
			if (other.moderator != null)
				return false;
		} else if (!moderator.equals(other.moderator))
			return false;
		if (muted == null) {
			if (other.muted != null)
				return false;
		} else if (!muted.equals(other.muted))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (warning == null) {
			if (other.warning != null)
				return false;
		} else if (!warning.equals(other.warning))
			return false;
		return true;
	}
	
	public User(int id, String username, String password, Boolean muted, Boolean banned,
			Boolean moderator, String warning) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.muted = muted;
		this.banned = banned;
		this.moderator = moderator;
		this.warning = warning;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", muted="
				+ muted + ", banned=" + banned + ", moderator=" + moderator + ", warning=" + warning
				+ "]";
	}
}
