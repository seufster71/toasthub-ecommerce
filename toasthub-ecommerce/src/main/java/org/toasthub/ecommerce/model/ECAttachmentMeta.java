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

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ec_attachment_meta")
public class ECAttachmentMeta extends ECBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String title;
	protected Long size;
	protected String contentType;
	protected ECAttachment attachment;
	//protected AttachmentThumbnail thumbNail;
	protected ECStoreItem storeItem;
	
	public ECAttachmentMeta() {
		super();
	}
	public ECAttachmentMeta(String title, long size, String contentType , String processType, ECAttachment file){
		this.setTitle(title);
		this.setActive(true);
		this.setArchive(false);
		this.setLocked(false);
		this.setCreated(new Date());
		this.setSize(size);
		this.setContentType(contentType);
		this.setAttachment(file);
	}
	
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "size")
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	
	@Column(name = "content_type")
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@JsonIgnore
	@ManyToOne(targetEntity = ECAttachment.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "attachment_id")
	public ECAttachment getAttachment() {
		return attachment;
	}
	public void setAttachment(ECAttachment attachment) {
		this.attachment = attachment;
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
	
	
}
