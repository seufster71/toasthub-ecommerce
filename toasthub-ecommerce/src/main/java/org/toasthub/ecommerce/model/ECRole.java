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
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.toasthub.core.general.api.View;
import org.toasthub.core.general.model.BaseEntity;
import org.toasthub.core.general.model.Text;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "ec_role")
public class ECRole extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected String code;
	protected Set<ECRolePermission> permissions;
	protected Instant startDate;
	protected Instant endDate;
	
	//Constructor
	public ECRole() {
		super();
	}
	
	public ECRole(String code, Text title, Boolean defaultLang, String dir){
		this.setActive(true);
		this.setArchive(false);
		this.setLocked(false);
		this.setCreated(Instant.now());
	}

	public ECRole(boolean active, boolean archive, boolean locked, String name, String code, Instant startDate, Instant endDate) {
		this.setActive(active);
		this.setArchive(archive);
		this.setLocked(locked);
		this.setName(name);
		this.setCode(code);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}
	
	// Methods
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	public Set<ECRolePermission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<ECRolePermission> permissions) {
		this.permissions = permissions;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "start_date")
	public Instant getStartDate() {
		return startDate;
	}
	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "end_date")
	public Instant getEndDate() {
		return endDate;
	}
	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

}
