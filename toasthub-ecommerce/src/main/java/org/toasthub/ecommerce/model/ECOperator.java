package org.toasthub.ecommerce.model;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ec_store_operator")
public class ECOperator extends ECBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected ECStore store;
	protected ECMember member;
	// list of operator roles

	
	public ECOperator(){
	}
	
	// Setters/Getters
	@JsonIgnore
	@ManyToOne(targetEntity = ECStore.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id") 
	public ECStore getStore() {
		return store;
	}
	public void setStore(ECStore store) {
		this.store = store;
	}
	
	@JsonIgnore
	@ManyToOne(targetEntity = ECMember.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id") 
	public ECMember getMember() {
		return member;
	}
	public void setMember(ECMember member) {
		this.member = member;
	}
	
	
}
