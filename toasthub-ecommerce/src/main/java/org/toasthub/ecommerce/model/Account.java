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
@Table(name = "ec_account")
public class Account extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected UserRef user;
	protected BigDecimal balance;
	protected String type;
	protected BigDecimal balanceCryptoCurrency;
	protected String typeCryptoCurrency;
	
	public Account(){
	}
	
	// Setters/Getters
	@JsonIgnore
	@ManyToOne(targetEntity = UserRef.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "user_ref_id") 
	public UserRef getUser() {
		return user;
	}
	public void setUser(UserRef user) {
		this.user = user;
	}
	
	@JsonView({View.Admin.class,View.Member.class})
	@Column(name = "balance")
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@JsonView({View.Admin.class,View.Member.class})
	@Column(name = "type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@JsonView({View.Admin.class,View.Member.class})
	@Column(name = "balance_cryto")
	public BigDecimal getBalanceCryptoCurrency() {
		return balanceCryptoCurrency;
	}
	public void setBalanceCryptoCurrency(BigDecimal balanceCryptoCurrency) {
		this.balanceCryptoCurrency = balanceCryptoCurrency;
	}

	@JsonView({View.Admin.class,View.Member.class})
	@Column(name = "type_cryto")
	public String getTypeCryptoCurrency() {
		return typeCryptoCurrency;
	}

	public void setTypeCryptoCurrency(String typeCryptoCurrency) {
		this.typeCryptoCurrency = typeCryptoCurrency;
	}

}
