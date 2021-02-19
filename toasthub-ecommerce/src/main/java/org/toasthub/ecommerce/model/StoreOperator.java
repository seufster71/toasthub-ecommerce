package org.toasthub.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "ec_store_operator")
public class StoreOperator extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Store store;
	protected UserRef user;
	

	
	public StoreOperator(){
	}
	
	// Setters/Getters
	@JsonIgnore
	@ManyToOne(targetEntity = Store.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id") 
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	
	@JsonIgnore
	@ManyToOne(targetEntity = UserRef.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "user_ref_id") 
	public UserRef getUser() {
		return user;
	}
	public void setUser(UserRef user) {
		this.user = user;
	}
	
	
}
