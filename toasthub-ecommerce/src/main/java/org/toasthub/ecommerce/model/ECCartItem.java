/*
 * Copyright (C) 2023 The ToastHub Project
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

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "ec_cart_item")
public class ECCartItem extends ECBaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	protected ECCart cart;
	protected ECStoreItem item;
	protected int quantity;
	
	protected int inventory;

	// Constructor
	public ECCartItem() {
		super();
	}

	// Setters and Getters
	@ManyToOne(targetEntity = ECCart.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id") 
	public ECCart getCart() {
		return cart;
	}
	public void setCart(ECCart cart) {
		this.cart = cart;
	}

	@ManyToOne(targetEntity = ECStoreItem.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "store_item_id") 
	public ECStoreItem getItem() {
		return item;
	}
	public void setItem(ECStoreItem item) {
		this.item = item;
	}

	@Column(name = "quantity_item")
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Transient
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	
	
}