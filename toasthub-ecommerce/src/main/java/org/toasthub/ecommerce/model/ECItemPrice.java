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
@Table(name = "ec_item_price")
public class ECItemPrice extends ECBaseEntity {

	private static final long serialVersionUID = 1L;

	protected ECStoreItem storeItem;
	protected BigDecimal price;
	protected String priceType;
	protected BigDecimal overridePrice;
	
	// constructors
	public ECItemPrice() {}
	
	// Methods
	@JsonIgnore
	@ManyToOne(targetEntity = ECStoreItem.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id") 
	public ECStoreItem getStoreItem() {
		return storeItem;
	}
	public void setStoreItem(ECStoreItem storeItem) {
		this.storeItem = storeItem;
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
	
}
