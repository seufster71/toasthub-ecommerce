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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "ec_cart_item")
public class CartItem extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	protected StoreItem item;
	protected int quantity;
	protected BigDecimal totalPrice;
	protected BigDecimal totalPriceCrypto;
	protected Long userId;
	
	// Transient
	protected int availableQuantity;

	// Constructor
	public CartItem() {
		super();
	}



	@ManyToOne(targetEntity = StoreItem.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "store_item_id") 
	public StoreItem getItem() {
		return item;
	}
	public void setItem(StoreItem item) {
		this.item = item;
	}

	@Column(name = "quantity_item")
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Column(name = "total_price")
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Column(name = "total_price_crypto")
	public BigDecimal getTotalPriceCrypto() {
		return totalPriceCrypto;
	}
	public void setTotalPriceCrypto(BigDecimal totalPriceCrypto) {
		this.totalPriceCrypto = totalPriceCrypto;
	}

	@Column(name = "user_ref_id") 
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Transient
	public int getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}


}