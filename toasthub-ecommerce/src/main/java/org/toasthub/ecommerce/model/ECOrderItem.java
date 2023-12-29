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
@Table(name = "ec_order_item")
public class ECOrderItem extends ECBaseEntity {

	private static final long serialVersionUID = 1L;

	protected ECOrder order;
	protected ECStoreItem storeItem;
	protected int quantity;
	protected int discountPercent;
	protected BigDecimal discountValue;
	protected BigDecimal unitPrice;
	protected BigDecimal unitPriceCrypto;
	protected BigDecimal salePrice;
	protected BigDecimal salePriceCrypto;
	protected BigDecimal totalPrice;
	protected BigDecimal totalPriceCrypto;
	
	// constructors
	public ECOrderItem() {}
	
	public ECOrderItem(Long id, int quantity) {
		this.setId(id);
		this.setQuantity(quantity);
	}
	
	// Methods
	@JsonIgnore
	@ManyToOne(targetEntity = ECOrder.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id") 
	public ECOrder getOrder() {
		return order;
	}
	public void setOrder(ECOrder order) {
		this.order = order;
	}
	
	
	@JsonIgnore
	@ManyToOne(targetEntity = ECStoreItem.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "store_item_id") 
	public ECStoreItem getStoreItem() {
		return storeItem;
	}
	public void setStoreItem(ECStoreItem storeItem) {
		this.storeItem = storeItem;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "discount_percent")
	public int getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(int discountPercent) {
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
	@Column(name = "unit_price")
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "unit_price_crypto")
	public BigDecimal getUnitPriceCrypto() {
		return unitPriceCrypto;
	}
	public void setUnitPriceCrypto(BigDecimal unitPriceCrypto) {
		this.unitPriceCrypto = unitPriceCrypto;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "sale_price")
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "sale_price_crypto")
	public BigDecimal getSalePriceCrypto() {
		return salePriceCrypto;
	}
	public void setSalePriceCrypto(BigDecimal salePriceCrypto) {
		this.salePriceCrypto = salePriceCrypto;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "total_price")
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "total_price_crypto")
	public BigDecimal getTotalPriceCrypto() {
		return totalPriceCrypto;
	}
	public void setTotalPriceCrypto(BigDecimal totalPriceCrypto) {
		this.totalPriceCrypto = totalPriceCrypto;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "quantity")
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
