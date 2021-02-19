package org.toasthub.ecommerce.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ec_store_operator")
public class StoreOperator extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Store store;
	protected Member member;
	// list of operator roles

	
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
	@ManyToOne(targetEntity = Member.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id") 
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	
	
}
