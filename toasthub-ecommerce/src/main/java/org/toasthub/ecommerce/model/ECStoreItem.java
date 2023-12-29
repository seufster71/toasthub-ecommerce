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

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "ec_store_item")
public class ECStoreItem extends ECBaseEntity {

	private static final long serialVersionUID = 1L;

	protected ECStore store;
	protected String name;
	protected String description;
	protected BigDecimal price;
	protected BigDecimal priceCrypto;
	protected BigDecimal discountPercent;
	protected BigDecimal discountValue;
	protected BigDecimal discountCryptoValue;
	protected LocalDate discountStartDate;
	protected LocalDate discountEndDate;
	protected int inventoryCount;
	protected String size;
	protected String color;
	protected String category;
	protected LocalDate availableStartDate;
	protected LocalDate availableEndDate;
	protected Set<ECAttachmentMeta> attachments;
	
	// constructors
	public ECStoreItem() {}
	
	public ECStoreItem(Long id, String name, int inventoryCount) {
		this.setId(id);
		this.setName(name);
		this.setInventoryCount(inventoryCount);
	}
	
	public ECStoreItem(Long id, String name, int inventoryCount, String size, String color) {
		this.setId(id);
		this.setName(name);
		this.setInventoryCount(inventoryCount);
		this.setSize(size);
		this.setColor(color);
	}
	
	// Methods
	@JsonIgnore
	@ManyToOne(targetEntity = ECStore.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id") 
	public ECStore getStore() {
		return store;
	}
	public void setStore(ECStore store) {
		this.store = store;
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
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "price")
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
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
	@Column(name = "discount_percent")
	public BigDecimal getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(BigDecimal discountPercent) {
		this.discountPercent = discountPercent;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "discount_value")
	public BigDecimal getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(BigDecimal discountValue) {
		this.discountValue = discountValue;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "discount_crypt_value")
	public BigDecimal getDiscountCryptoValue() {
		return discountCryptoValue;
	}
	public void setDiscountCryptoValue(BigDecimal discountCryptoValue) {
		this.discountCryptoValue = discountCryptoValue;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "discount_start_date")
	public LocalDate getDiscountStartDate() {
		return discountStartDate;
	}
	public void setDiscountStartDate(LocalDate discountStartDate) {
		this.discountStartDate = discountStartDate;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "discount_end_date")
	public LocalDate getDiscountEndDate() {
		return discountEndDate;
	}
	public void setDiscountEndDate(LocalDate discountEndDate) {
		this.discountEndDate = discountEndDate;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "inventory_count")
	public int getInventoryCount() {
		return inventoryCount;
	}
	public void setInventoryCount(int inventoryCount) {
		this.inventoryCount = inventoryCount;
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
	@Column(name = "available_start_date")
	public LocalDate getAvailableStartDate() {
		return availableStartDate;
	}
	public void setAvailableStartDate(LocalDate availableStartDate) {
		this.availableStartDate = availableStartDate;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "available_end_date")
	public LocalDate getAvailableEndDate() {
		return availableEndDate;
	}
	public void setAvailableEndDate(LocalDate availableEndDate) {
		this.availableEndDate = availableEndDate;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@OneToMany(mappedBy = "storeItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<ECAttachmentMeta> getAttachments() {
		return attachments;
	}
	public void setAttachments(Set<ECAttachmentMeta> attachments) {
		this.attachments = attachments;
	}
}
