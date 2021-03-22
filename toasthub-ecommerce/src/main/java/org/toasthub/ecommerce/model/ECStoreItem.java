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
public class ECStoreItem extends ECBaseEntity {

	private static final long serialVersionUID = 1L;

	protected ECStore store;
	protected String name;
	protected String description;
	protected int quantity;
	protected String size;
	protected String color;
	protected String category;
	protected boolean available;
	protected LocalDate availableDate;
	protected LocalDate disableDate;
	protected Set<ECAttachmentMeta> attachments;
	
	// constructors
	public ECStoreItem() {}
	
	public ECStoreItem(Long id, String name, int quantity) {
		this.setId(id);
		this.setName(name);
		this.setQuantity(quantity);
	}
	
	public ECStoreItem(Long id, String name, int quantity, String size, String color) {
		this.setId(id);
		this.setName(name);
		this.setQuantity(quantity);
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
	public Set<ECAttachmentMeta> getAttachments() {
		return attachments;
	}
	public void setAttachments(Set<ECAttachmentMeta> attachments) {
		this.attachments = attachments;
	}
}
