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

package org.toasthub.ecommerce.order;

import java.util.List;
import java.util.Map;

import org.toasthub.core.general.model.OrderCriteria;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.core.general.model.SearchCriteria;
import org.toasthub.ecommerce.model.ECStoreItem;
import org.toasthub.ecommerce.model.ECMember;
import org.toasthub.ecommerce.model.ECOrder;

public interface ECOrderDao {
	public Long pendingCount(List<OrderCriteria> orderCriteria,SearchCriteria searchCriteria) throws Exception;
	public List<ECOrder> pendingList(List<OrderCriteria> orderCriteria,SearchCriteria searchCriteria, Integer listStart, Integer listLimit) throws Exception;
	public Long completedCount(List<OrderCriteria> orderCriteria,SearchCriteria searchCriteria) throws Exception;
	public List<ECOrder> completedList(List<OrderCriteria> orderCriteria,SearchCriteria searchCriteria, Integer listStart, Integer listLimit) throws Exception;
	public ECOrder create(ECOrder purchaseRequest, ECMember user, ECStoreItem storeItem) throws Exception;
	public Long pendingStats();
	public List<Map<String,Long>> completedStats();
	
	public Long memberPendingCount(ECMember user,List<OrderCriteria> orderCriteria,SearchCriteria searchCriteria) throws Exception;
	public List<ECOrder> memberPendingList(ECMember user,List<OrderCriteria> orderCriteria,SearchCriteria searchCriteria, Integer listStart, Integer listLimit) throws Exception;
	public Long memberCompletedCount(ECMember user,List<OrderCriteria> orderCriteria,SearchCriteria searchCriteria) throws Exception;
	public List<ECOrder> memberCompletedList(ECMember user,List<OrderCriteria> orderCriteria,SearchCriteria searchCriteria, Integer listStart, Integer listLimit) throws Exception;
	
	public List<String> processPurchaseRequest(ECOrder[] purchaseRequests,RestResponse response, ECMember pepUser) throws Exception;
}
