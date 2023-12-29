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

package org.toasthub.ecommerce.admin.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.toasthub.core.common.EntityManagerDataSvc;
import org.toasthub.core.common.UtilSvc;
import org.toasthub.core.general.model.GlobalConstant;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.core.preference.model.PrefCacheUtil;
import org.toasthub.ecommerce.market.ECMarketDaoImpl;
import org.toasthub.ecommerce.model.ECAttachment;
import org.toasthub.ecommerce.model.ECAttachmentMeta;
import org.toasthub.ecommerce.model.ECMarket;

@Repository("ECMarketAdminDao")
@Transactional("TransactionManagerData")
public class ECMarketAdminDaoImpl extends ECMarketDaoImpl implements ECMarketAdminDao {

	@Autowired
	protected EntityManagerDataSvc entityManagerDataSvc;
	@Autowired
	protected UtilSvc utilSvc;
	@Autowired
	PrefCacheUtil prefCacheUtil;

	@Override
	public void delete(RestRequest request, RestResponse response) {
		if (request.containsParam(GlobalConstant.ITEMID) && !"".equals(request.getParam(GlobalConstant.ITEMID))) {
			if (request.containsParam(GlobalConstant.HARDDELETE) && !"YES".equals(request.getParam(GlobalConstant.HARDDELETE))) {
				ECMarket marketItem = (ECMarket) entityManagerDataSvc.getInstance().getReference(ECMarket.class, request.getParamLong(GlobalConstant.ITEMID));
				entityManagerDataSvc.getInstance().remove(marketItem);
				
			} else {
				// soft delete store items
				ECMarket marketItem = entityManagerDataSvc.getInstance().find(ECMarket.class, request.getParamLong(GlobalConstant.ITEMID));
				marketItem.setActive(false);
				marketItem.setArchive(true);
				entityManagerDataSvc.getInstance().merge(marketItem);
			}
		} else {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Missing ID", response);
		}
	}

	@Override
	public void save(RestRequest request, RestResponse response) {
		ECMarket market = (ECMarket) request.getParam(GlobalConstant.ITEM);
		entityManagerDataSvc.getInstance().merge(market);
	}

	@Override
	public ECAttachment getAttachment(ECAttachmentMeta attachmentMeta) throws Exception {
		return entityManagerDataSvc.getInstance().find(ECAttachment.class, attachmentMeta.getAttachment().getId());
	}

	@Override
	public ECAttachmentMeta getAttachment(Long id) throws Exception {
		String HQLQuery = "SELECT a FROM ECAttachmentMeta AS a JOIN FETCH a.file WHERE a.id=:id ";
		return (ECAttachmentMeta) entityManagerDataSvc.getInstance().createQuery(HQLQuery).setParameter("id", id).getSingleResult();
	}

	@Override
	public void deleteAttachment(Long id) throws Exception {
		ECAttachmentMeta attachmentMeta = entityManagerDataSvc.getInstance().find(ECAttachmentMeta.class, id);
		entityManagerDataSvc.getInstance().remove(attachmentMeta);
		entityManagerDataSvc.getInstance().flush();
		entityManagerDataSvc.getInstance().clear();
	}

	@Override
	public ECAttachmentMeta saveAttachment(ECAttachmentMeta attachmentMeta) throws Exception {
		return entityManagerDataSvc.getInstance().merge(attachmentMeta);
	}

}
