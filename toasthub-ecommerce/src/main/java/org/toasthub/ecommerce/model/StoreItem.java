/*
 * Copyright (C) 2016 The ToastHub Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.toasthub.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "ec_store_item")
public class StoreItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	protected Store store;
	protected String title;
	protected String description;
	protected BigDecimal price;
	protected String priceType;
	protected BigDecimal overridePrice;
	protected BigDecimal priceCrypto;
	protected String priceCryptoType;
	protected BigDecimal overridePriceCrypto;
	protected int quantity;
	protected String size;
	protected String color;
	protected String category;
	protected boolean available;
	protected LocalDate availableDate;
	protected LocalDate disableDate;
	protected Set<AttachmentMeta> attachments;
	
	// constructors
	public StoreItem() {}
	
	public StoreItem(Long id, String title, int quantity) {
		this.setId(id);
		this.setTitle(title);
		this.setQuantity(quantity);
	}
	
	public StoreItem(Long id, String title, int quantity, String size, String color) {
		this.setId(id);
		this.setTitle(title);
		this.setQuantity(quantity);
		this.setSize(size);
		this.setColor(color);
	}
	
	// Methods
	@JsonIgnore
	@ManyToOne(targetEntity = Store.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id") 
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	
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
	@Column(name = "price")
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "price_type")
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "override_price")
	public BigDecimal getOverridePrice() {
		return overridePrice;
	}
	public void setOverridePrice(BigDecimal overridePrice) {
		this.overridePrice = overridePrice;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "price_crypto")
	public BigDecimal getPriceCrypto() {
		return priceCrypto;
	}
	public void setPriceCrypto(BigDecimal priceCrypto) {
		this.priceCrypto = priceCrypto;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "price_crypto_type")
	public String getPriceCryptoType() {
		return priceCryptoType;
	}
	public void setPriceCryptoType(String priceCryptoType) {
		this.priceCryptoType = priceCryptoType;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "override_price_crypto")
	public BigDecimal getOverridePriceCrypto() {
		return overridePriceCrypto;
	}
	public void setOverridePriceCrypto(BigDecimal overridePriceCrypto) {
		this.overridePriceCrypto = overridePriceCrypto;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "quantity")
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "size")
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "color")
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "category")
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "available")
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "available_date")
	public LocalDate getAvailableDate() {
		return availableDate;
	}
	public void setAvailableDate(LocalDate availableDate) {
		this.availableDate = availableDate;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "disable_date")
	public LocalDate getDisableDate() {
		return disableDate;
	}
	public void setDisableDate(LocalDate disableDate) {
		this.disableDate = disableDate;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@OneToMany(mappedBy = "storeItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<AttachmentMeta> getAttachments() {
		return attachments;
	}
	public void setAttachments(Set<AttachmentMeta> attachments) {
		this.attachments = attachments;
	}
}
