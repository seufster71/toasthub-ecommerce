package org.toasthub.ecommerce.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "ec_member")
public class ECMember extends ECBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Long userId;
	protected String username;
	
	
	public ECMember(Long id, String username){
		this.setUsername(username);
	}
	
	// Setters/Getters
	@JsonView({View.Admin.class,View.Member.class})
	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@JsonView({View.Admin.class,View.Member.class})
	@Column(name = "username")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
