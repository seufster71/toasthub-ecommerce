package org.toasthub.ecommerce.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "ec_market")
public class ECMarket extends ECBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected String title;
	protected String description;
	protected String pickup;
	protected String imgUrl;
	protected String url;
	

	
	public ECMarket(){
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

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "pickup")
	public String getPickup() {
		return pickup;
	}
	public void setPickup(String pickup) {
		this.pickup = pickup;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "image_url")
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
