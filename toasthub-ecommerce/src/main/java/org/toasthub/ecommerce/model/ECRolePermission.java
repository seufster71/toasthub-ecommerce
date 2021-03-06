/*
 * Copyright (C) 2020 The ToastHub Project
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

/**
 * @author Edward H. Seufert
 */

package org.toasthub.ecommerce.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.toasthub.core.general.api.View;
import org.toasthub.core.general.model.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
@Entity
@Table(name = "ec_role_permission")
public class ECRolePermission extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected ECRole role;
	protected ECPermission permission;
	protected String rights;
	protected Instant startDate;
	protected Instant endDate;
	
	// transient
	protected String code;
	protected Long permissionId;

	//Constructor
	public ECRolePermission(){}
	
	public ECRolePermission(ECRole role, ECPermission permission) {
		this.setRole(role);
		this.setPermission(permission);
	}
	
	public ECRolePermission(Long id, boolean active, String rights, Instant startDate, Instant endDate, Long permissionId) {
		this.setId(id);
		this.setActive(active);
		this.setRights(rights);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setPermissionId(permissionId);
	}
	
	public ECRolePermission(boolean active, boolean archive, boolean locked, Instant startDate, Instant endDate, String rights, ECRole role, ECPermission permission) {
		this.setActive(active);
		this.setActive(active);
		this.setLocked(locked);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setRights(rights);
		this.setRole(role);
		this.setPermission(permission);
	}

	// Methods
	@JsonIgnore
	@ManyToOne(targetEntity = ECRole.class)
	@JoinColumn(name = "role_id")
	public ECRole getRole() {
		return role;
	}
	public void setRole(ECRole role) {
		this.role = role;
	}
	
	@JsonIgnore
	@ManyToOne(targetEntity = ECPermission.class)
	@JoinColumn(name = "permission_id")
	public ECPermission getPermission() {
		return permission;
	}
	public void setPermission(ECPermission permission) {
		this.permission = permission;
	}
	
	@JsonView({View.Member.class,View.Admin.class,View.System.class})
	@Column(name = "rights")
	public String getRights() {
		return rights;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}
	
	@JsonView({View.Member.class,View.Admin.class,View.System.class})
	@Column(name = "start_date")
	public Instant getStartDate() {
		return startDate;
	}
	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}
	
	@JsonView({View.Member.class,View.Admin.class,View.System.class})
	@Column(name = "end_date")
	public Instant getEndDate() {
		return endDate;
	}
	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}
	
	@JsonView({View.Admin.class})
	@Transient
	public Long getPermissionId() {
		if (this.permission == null) {
			return this.permissionId;
		} else {
			return this.permission.getId();
		}
	}
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	
	@JsonView({View.Admin.class})
	@Transient
	public String getCode() {
		if (this.permission == null) {
			return this.code;
		} else {
			return permission.getCode();
		}
	}
	
}
