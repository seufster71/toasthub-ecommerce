package org.toasthub.ecommerce.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "ec_store")
public class Store extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected String title;
	protected String description;
	// list of operators

	
	public Store(){
	}
	
	// Setters/Getters
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
