package org.toasthub.ecommerce.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "ec_cart")
public class ECCart extends ECBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected ECMember member;
	protected ECCurrency currency;
	

	
	public ECCart(){
	}
	
	// Setters/Getters
	@JsonIgnore
	@ManyToOne(targetEntity = ECMember.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id") 
	public ECMember getMember() {
		return member;
	}
	public void setMember(ECMember member) {
		this.member = member;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@ManyToOne(targetEntity = ECCurrency.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id")
	public ECCurrency getCurrency() {
		return currency;
	}
	public void setCurrency(ECCurrency currency) {
		this.currency = currency;
	}
	
	
}
