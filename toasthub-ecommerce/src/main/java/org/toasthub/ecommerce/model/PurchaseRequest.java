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

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "ec_purchase_request")
public class PurchaseRequest extends BaseEntity {
	private static final long serialVersionUID = 1L;

	protected Member requester;
	protected String requesterName;
	protected StoreItem item;
	protected String itemName;
	protected int quantity;
	protected BigDecimal price;
	protected BigDecimal totalPrice;
	protected Boolean declined;
	protected Boolean approved;
	protected String itemSize;
	protected String itemColor;

	// Constructor
	public PurchaseRequest() {}

	@JsonIgnore
	@ManyToOne(targetEntity = Member.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "requester_id") 
	public Member getRequester() {
		return requester;
	}
	public void setRequester(Member requester) {
		this.requester = requester;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "requester_name")
	public String getRequesterName() {
		return requesterName;
	}
	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}

	@JsonIgnore
	@ManyToOne(targetEntity = StoreItem.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	public StoreItem getItem() {
		return item;
	}
	public void setItem(StoreItem item) {
		this.item = item;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "item_name")
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "quantity_item")
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "item_price")
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name = "total_items_price")
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "is_declined")
	public Boolean isDeclined() {
		return declined;
	}
	public void setDeclined(Boolean declined) {
		this.declined = declined;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "is_approved")
	public Boolean isApproved() {
		return approved;
	}
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Transient
	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Transient
	public String getItemColor() {
		return itemColor;
	}

	public void setItemColor(String itemColor) {
		this.itemColor = itemColor;
	}
}