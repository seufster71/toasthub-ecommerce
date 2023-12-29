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
@Table(name = "ec_receipt")
public class ECReceipt extends ECBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected ECOrder order;
	
	public ECReceipt(){
	}
	
	// Setters/Getters
	@JsonIgnore
	@ManyToOne(targetEntity = ECOrder.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id") 
	public ECOrder getOrder() {
		return order;
	}
	public void setOrder(ECOrder order) {
		this.order = order;
	}
	
}
