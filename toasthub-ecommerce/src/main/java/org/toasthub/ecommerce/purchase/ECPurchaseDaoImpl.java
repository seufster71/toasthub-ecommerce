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

package org.toasthub.ecommerce.purchase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.toasthub.core.common.EntityManagerDataSvc;
import org.toasthub.core.general.model.OrderCriteria;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.core.general.model.SearchCriteria;
import org.toasthub.ecommerce.model.ECPurchaseRequest;
import org.toasthub.ecommerce.model.ECStoreItem;
import org.toasthub.ecommerce.model.ECMember;


@Transactional(rollbackOn = Exception.class)
@Repository("PurchaseDao")
public class ECPurchaseDaoImpl implements ECPurchaseDao {

	@Autowired
	protected EntityManagerDataSvc entityManagerDataSvc;

	@Override
	public ECPurchaseRequest create(ECPurchaseRequest purchaseRequest, ECMember user, ECStoreItem storeItem) throws Exception {
		purchaseRequest = entityManagerDataSvc.getInstance().merge(purchaseRequest);
		user = entityManagerDataSvc.getInstance().merge(user);
		storeItem = entityManagerDataSvc.getInstance().merge(storeItem);

		return entityManagerDataSvc.getInstance().merge(purchaseRequest);
	}
	@Override
	public ECPurchaseRequest approve(Long id) throws Exception {
		ECPurchaseRequest request = entityManagerDataSvc.getInstance().find(ECPurchaseRequest.class, id);
		request.setApproved(true);
		request.setDeclined(false);
		request.getRequester();
		
		return entityManagerDataSvc.getInstance().merge(request);
	}

	@Override
	public ECPurchaseRequest deny(Long id) throws Exception {
		ECPurchaseRequest request = entityManagerDataSvc.getInstance().find(ECPurchaseRequest.class, id);
		request.setApproved(false);
		request.setDeclined(true);

		// return user points
	//	UserRef user = request.getRequester();
	//	int total = user.getUserPoints() + (request.getCost() * request.getQuantity());
	//	user.setUserPoints(total);
	
		// replace item quantity
		int quantity = request.getItem().getQuantity() + request.getQuantity();
		request.getItem().setQuantity(quantity);

		entityManagerDataSvc.getInstance().merge(request);
	//	entityManager.merge(user);
		return request;
	}

	@Override
	public Long pendingCount(List<OrderCriteria> orderCriteria, SearchCriteria searchCriteria) throws Exception {
		String HQLQuery = "SELECT COUNT(*) FROM ECPurchaseRequest AS r WHERE r.declined=false AND r.approved=false ";
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("PENDING_REQUESTER_NAME".equals(searchCriteria.getSearchColumn())) {
				HQLQuery += " AND r.requesterName like :searchValue";
			}
		}

		Query query = entityManagerDataSvc.getInstance().createQuery(HQLQuery);
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("PENDING_REQUESTER_NAME".equals(searchCriteria.getSearchColumn())) {
				query.setParameter("searchValue", "%"+searchCriteria.getSearchValue()+"%");
			}
		}
		Long count = (Long) query.getSingleResult();
		if (count == null) {
			count = 0l;
		}
		return count;
	}

	@Override
	public List<ECPurchaseRequest> pendingList(List<OrderCriteria> orderCriteria, SearchCriteria searchCriteria,
			Integer listStart, Integer listLimit) throws Exception {
		String HQLQuery = "SELECT DISTINCT r FROM ECPurchaseRequest AS r WHERE r.declined=false AND r.approved=false ";
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("PENDING_REQUESTER_NAME".equals(searchCriteria.getSearchColumn())) {
				HQLQuery += " AND r.requesterName like :searchValue";
			}
		}
		if (orderCriteria != null) {
			HQLQuery += " order by r.created DESC";
		} else {
			HQLQuery += " order by r.created DESC";
		}

		Query query = entityManagerDataSvc.getInstance().createQuery(HQLQuery);
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("PENDING_REQUESTER_NAME".equals(searchCriteria.getSearchColumn())) {
				query.setParameter("searchValue", "%"+searchCriteria.getSearchValue()+"%");
			}
		}
		if (listStart != null && listLimit != null){
			query.setFirstResult(listStart);
			query.setMaxResults(listLimit);
		}
		@SuppressWarnings("unchecked")
		List<ECPurchaseRequest> resultList = query.getResultList();
		// add size and color
		for (ECPurchaseRequest p : resultList) {
			p.setItemSize(p.getItem().getSize());
			p.setItemColor(p.getItem().getColor());
		}
		return resultList;
	}

	@Override
	public Long completedCount(List<OrderCriteria> orderCriteria, SearchCriteria searchCriteria) throws Exception {
		String HQLQuery = "SELECT COUNT(*) FROM ECPurchaseRequest AS r WHERE (r.declined=true OR r.approved=true) ";
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("COMPLETED_REQUESTER_NAME".equals(searchCriteria.getSearchColumn())) {
				HQLQuery += " AND r.requesterName like :searchValue";
			}
		}

		Query query = entityManagerDataSvc.getInstance().createQuery(HQLQuery);
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("COMPLETED_REQUESTER_NAME".equals(searchCriteria.getSearchColumn())) {
				query.setParameter("searchValue", "%"+searchCriteria.getSearchValue()+"%");
			}
		}
		Long count = (Long) query.getSingleResult();
		if (count == null) {
			count = 0l;
		}
		return count;
	}

	@Override
	public List<ECPurchaseRequest> completedList(List<OrderCriteria> orderCriteria, SearchCriteria searchCriteria,
			Integer listStart, Integer listLimit) throws Exception {
		String HQLQuery = "SELECT DISTINCT r FROM ECPurchaseRequest AS r WHERE (r.declined=true OR r.approved=true) ";
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("COMPLETED_REQUESTER_NAME".equals(searchCriteria.getSearchColumn())) {
				HQLQuery += " AND r.requesterName like :searchValue";
			}
		}
		if (orderCriteria != null) {
			HQLQuery += " order by r.modified DESC";
		} else {
			HQLQuery += " order by r.modified DESC";
		}

		Query query = entityManagerDataSvc.getInstance().createQuery(HQLQuery);
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("COMPLETED_REQUESTER_NAME".equals(searchCriteria.getSearchColumn())) {
				query.setParameter("searchValue", "%"+searchCriteria.getSearchValue()+"%");
			}
		}
		if (listStart != null && listLimit != null){
			query.setFirstResult(listStart);
			query.setMaxResults(listLimit);
		}
		@SuppressWarnings("unchecked")
		List<ECPurchaseRequest> resultList = query.getResultList();
		// add size and color
		for (ECPurchaseRequest p : resultList) {
			p.setItemSize(p.getItem().getSize());
			p.setItemColor(p.getItem().getColor());
		}
		return resultList;
	}
	@Override
	public Long memberPendingCount(ECMember user, List<OrderCriteria> orderCriteria, SearchCriteria searchCriteria)
			throws Exception {
		String HQLQuery = "SELECT COUNT(*) FROM ECPurchaseRequest AS r WHERE r.declined=false AND r.approved=false AND r.requester.id = :id";
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("PENDING_ITEM_NAME".equals(searchCriteria.getSearchColumn())) {
				HQLQuery += " AND r.itemName like :searchValue";
			}
		}

		Query query = entityManagerDataSvc.getInstance().createQuery(HQLQuery);
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("PENDING_ITEM_NAME".equals(searchCriteria.getSearchColumn())) {
				query.setParameter("searchValue", "%"+searchCriteria.getSearchValue()+"%");
			}
		}
		query.setParameter("id", user.getId());
		Long count = (Long) query.getSingleResult();
		if (count == null) {
			count = 0l;
		}
		return count;
	}
	@Override
	public List<ECPurchaseRequest> memberPendingList(ECMember user, List<OrderCriteria> orderCriteria,
			SearchCriteria searchCriteria, Integer listStart, Integer listLimit) throws Exception {
		String HQLQuery = "SELECT DISTINCT r FROM ECPurchaseRequest AS r WHERE r.declined=false AND r.approved=false AND r.requester.id = :id";
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("PENDING_ITEM_NAME".equals(searchCriteria.getSearchColumn())) {
				HQLQuery += " AND r.itemName like :searchValue";
			}
		}
		if (orderCriteria != null) {
			HQLQuery += " order by r.created DESC";
		} else {
			HQLQuery += " order by r.created DESC";
		}

		Query query = entityManagerDataSvc.getInstance().createQuery(HQLQuery);
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("PENDING_ITEM_NAME".equals(searchCriteria.getSearchColumn())) {
				query.setParameter("searchValue", "%"+searchCriteria.getSearchValue()+"%");
			}
		}
		query.setParameter("id", user.getId());
		if (listStart != null && listLimit != null){
			query.setFirstResult(listStart);
			query.setMaxResults(listLimit);
		}
		@SuppressWarnings("unchecked")
		List<ECPurchaseRequest> resultList = query.getResultList();
		// add size and color
		for (ECPurchaseRequest p : resultList) {
			p.setItemSize(p.getItem().getSize());
			p.setItemColor(p.getItem().getColor());
		}
		return resultList;
	}
	@Override
	public Long memberCompletedCount(ECMember user, List<OrderCriteria> orderCriteria, SearchCriteria searchCriteria)
			throws Exception {
		String HQLQuery = "SELECT COUNT(*) FROM ECPurchaseRequest AS r WHERE (r.declined=true OR r.approved=true) AND r.requester.id = :id";
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("COMPLETED_ITEM_NAME".equals(searchCriteria.getSearchColumn())) {
				HQLQuery += " AND r.itemName like :searchValue";
			}
		}

		Query query = entityManagerDataSvc.getInstance().createQuery(HQLQuery);
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("COMPLETED_ITEM_NAME".equals(searchCriteria.getSearchColumn())) {
				query.setParameter("searchValue", "%"+searchCriteria.getSearchValue()+"%");
			}
		}
		query.setParameter("id", user.getId());
		Long count = (Long) query.getSingleResult();
		if (count == null) {
			count = 0l;
		}
		return count;
	}

	@Override
	public List<ECPurchaseRequest> memberCompletedList(ECMember user, List<OrderCriteria> orderCriteria,
			SearchCriteria searchCriteria, Integer listStart, Integer listLimit) throws Exception {
		String HQLQuery = "SELECT DISTINCT r FROM ECPurchaseRequest AS r WHERE (r.declined=true OR r.approved=true) AND r.requester.id = :id";
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("COMPLETED_ITEM_NAME".equals(searchCriteria.getSearchColumn())) {
				HQLQuery += " AND r.itemName like :searchValue";
			}
		}
		if (orderCriteria != null) {
			HQLQuery += " order by r.modified DESC";
		} else {
			HQLQuery += " order by r.modified DESC";
		}

		Query query = entityManagerDataSvc.getInstance().createQuery(HQLQuery);
		if (searchCriteria != null && searchCriteria.getSearchValue() != null && !"".equals(searchCriteria.getSearchValue()) 
				&& searchCriteria.getSearchColumn() != null && !"".equals(searchCriteria.getSearchColumn())) {
			if ("COMPLETED_ITEM_NAME".equals(searchCriteria.getSearchColumn())) {
				query.setParameter("searchValue", "%"+searchCriteria.getSearchValue()+"%");
			}
		}
		query.setParameter("id", user.getId());
		if (listStart != null && listLimit != null){
			query.setFirstResult(listStart);
			query.setMaxResults(listLimit);
		}
		@SuppressWarnings("unchecked")
		List<ECPurchaseRequest> resultList = query.getResultList();
		// add size and color
		for (ECPurchaseRequest p : resultList) {
			p.setItemSize(p.getItem().getSize());
			p.setItemColor(p.getItem().getColor());
		}
		return resultList;
	}

	@Override
	public Long pendingStats() {
		String HQLQuery = "SELECT COUNT(*) FROM ECPurchaseRequest WHERE declined=false AND approved=false";
		return (Long) entityManagerDataSvc.getInstance().createQuery(HQLQuery).getSingleResult();
	}

	@Override
	public List<Map<String,Long>> completedStats() {
		LocalDate localDate = LocalDate.now().minusDays(7);
		Date pastDate = java.sql.Date.valueOf(localDate);
		String HQLQuery = "SELECT cast(modified as date), COUNT(modified) FROM ECPurchaseRequest WHERE (declined=true OR approved=true) AND modified > :pastDate GROUP BY cast(modified as date) ORDER BY cast(modified as date) DESC";
		return (List<Map<String,Long>>) entityManagerDataSvc.getInstance().createQuery(HQLQuery).setParameter("pastDate", pastDate).getResultList();
	}
	
	@Override
	public List<String> processPurchaseRequest(ECPurchaseRequest[] purchaseRequests,RestResponse response, ECMember user) throws Exception {
		List<String> items = new ArrayList<String>();
	/*	for(PurchaseRequest purchaseRequest:purchaseRequests) {
			long cartItemId = Long.valueOf((long)purchaseRequest.getId());
			CartItem cartItem = entityManager.find(CartItem.class,cartItemId);
			long itemId = Long.valueOf(cartItem.getItem().getId());
			StoreItem storeItem = entityManager.find(StoreItem.class,itemId);
			int storeItemQuantity= storeItem.getQuantity();
			if(UserRef.getUserPoints() >= purchaseRequest.getTotalCost()) {
				if(storeItemQuantity > 0 && purchaseRequest.getQuantity() <= storeItemQuantity){
					purchaseRequest.setItem(storeItem);
					purchaseRequest.setItemName(storeItem.getTitle());
					String item = storeItem.getTitle();
					if (storeItem.getSize() != null) {
						item = item +" "+ storeItem.getSize();
					}
					if (storeItem.getColor() != null) {
						item = item +" "+ storeItem.getColor();
					}
					items.add(item);
					purchaseRequest.setActive(true); 
					purchaseRequest.setApproved(false);
					purchaseRequest.setArchive(false); 
					purchaseRequest.setDeclined(false); 
					purchaseRequest.setRequester(UserRef);
					purchaseRequest.setLocked(false);
					purchaseRequest.setRequesterName(UserRef.getFirstName()+" "+UserRef.getLastName());
					  
					storeItem.setQuantity(storeItemQuantity-purchaseRequest.getQuantity());
					  
					UserRef.setUserPoints(UserRef.getUserPoints() - purchaseRequest.getTotalCost());
					  
					entityManager.merge(purchaseRequest);
					entityManager.merge(UserRef);
					entityManager.merge(storeItem);
					 
					cartItem.setActive(false);
					cartItem.setArchive(true);
					entityManager.merge(cartItem);
					
					response.setStatus(GlobalConstants.SUCCESSFUL);
				} else { 
					response.addParam("ERROR_ITEM", storeItem.getId());
					response.setStatus(GlobalConstants.ERROR); Utils.addError(response,"There is not enough "+ storeItem.getTitle() + " in the store inventory for this purchase request."); 
					throw new Exception();
				}
			} else {
				response.setStatus(GlobalConstants.ERROR); Utils.addError( response,"You do not have enough points to make this purchase.");
				throw new Exception();
			}
		}	*/
		return items;
	}

}
