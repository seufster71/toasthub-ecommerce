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
public class AttachmentMeta extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String title;
	protected Long size;
	protected String contentType;
	protected Attachment attachment;
	//protected AttachmentThumbnail thumbNail;
	protected StoreItem storeItem;
	
	public AttachmentMeta() {
		super();
	}
	public AttachmentMeta(String title, long size, String contentType , String processType, Attachment file){
		this.setTitle(title);
		this.setActive(true);
		this.setArchive(false);
		this.setLocked(false);
		this.setCreated(new Date());
		this.setSize(size);
		this.setContentType(contentType);
		this.setFile(file);
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
	@ManyToOne(targetEntity = Attachment.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "attachment_id")
	public Attachment getAttachment() {
		return attachment;
	}
	public void setFile(Attachment attachment) {
		this.attachment = attachment;
	}
	
	@JsonIgnore
	@ManyToOne(targetEntity = StoreItem.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "store_item_id")
	public StoreItem getStoreItem() {
		return storeItem;
	}
	public void setStoreItem(StoreItem storeItem) {
		this.storeItem = storeItem;
	}
	
	
}
