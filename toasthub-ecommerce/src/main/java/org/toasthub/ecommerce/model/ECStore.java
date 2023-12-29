package org.toasthub.ecommerce.model;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "ec_store")
public class ECStore extends ECBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected ECMarket market;
	protected ECCurrency currency;
	protected String name;
	protected String description;
	// list of operators

	
	public ECStore(){
	}
	
	// Setters/Getters
	@JsonIgnore
	@ManyToOne(targetEntity = ECMarket.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "market_id") 
	public ECMarket getMarket() {
		return market;
	}
	public void setMarket(ECMarket market) {
		this.market = market;
	}
	
	@JsonIgnore
	@ManyToOne(targetEntity = ECCurrency.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id") 
	public ECCurrency getCurrency() {
		return currency;
	}

	public void setCurrency(ECCurrency currency) {
		this.currency = currency;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
