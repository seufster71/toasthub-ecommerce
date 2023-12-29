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
@Table(name = "ec_order")
public class ECOrder extends ECBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected ECMember member;
	protected ECStore store;
	protected String workflowStatus;
	

	
	public ECOrder(){
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
	@JoinColumn(name = "store_id")
	public ECStore getStore() {
		return store;
	}
	public void setStore(ECStore store) {
		this.store = store;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "workflow_status")
	public String getWorkflowStatus() {
		return workflowStatus;
	}
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
	
	
}
