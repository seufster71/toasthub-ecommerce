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

package org.toasthub.ecommerce.userRef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.toasthub.core.common.EntityManagerDataSvc;
import org.toasthub.core.common.UtilSvc;
import org.toasthub.core.preference.model.PrefCacheUtil;
import org.toasthub.ecommerce.model.UserRef;
import org.toasthub.security.model.User;

@Repository("ECUserRefDao")
@Transactional("TransactionManagerData")
public class UserRefDaoImpl implements UserRefDao {

	@Autowired
	protected EntityManagerDataSvc entityManagerDataSvc;
	@Autowired
	protected UtilSvc utilSvc;
	@Autowired
	PrefCacheUtil prefCacheUtil;
	
	
	
	@Override
	public UserRef getUserRef(User user) throws Exception {
		String HQLQuery = "SELECT x FROM UserRef AS x WHERE x.userRefId =: id ";
		return (UserRef) entityManagerDataSvc.getInstance().createQuery(HQLQuery).setParameter("id", user.getId()).getSingleResult();
	}

}
