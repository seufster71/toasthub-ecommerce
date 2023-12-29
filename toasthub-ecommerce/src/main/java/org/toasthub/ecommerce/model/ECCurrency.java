package org.toasthub.ecommerce.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "ec_currency")
public class ECCurrency extends ECBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected String type;
	protected Integer scale;

	
	public ECCurrency(){
	}
	
	// Setters/Getters
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "scale")
	public Integer getScale() {
		return scale;
	}
	public void setScale(Integer scale) {
		this.scale = scale;
	}
	
	
	
}
