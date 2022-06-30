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

package org.toasthub.ecommerce.cart;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.toasthub.core.common.EntityManagerDataSvc;
import org.toasthub.core.common.UtilSvc;
import org.toasthub.core.general.model.GlobalConstant;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.core.preference.model.PrefCacheUtil;
import org.toasthub.ecommerce.model.ECAttachment;
import org.toasthub.ecommerce.model.ECAttachmentMeta;
import org.toasthub.ecommerce.model.ECCartItem;


@Repository("ECCartDao")
@Transactional("TransactionManagerData")
public class ECCartDaoImpl implements ECCartDao {

	@Autowired
	protected EntityManagerDataSvc entityManagerDataSvc;
	@Autowired
	protected UtilSvc utilSvc;
	@Autowired
	PrefCacheUtil prefCacheUtil;

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
	public long totalCartItems(long userId) {
		String HQLQuery = "SELECT DISTINCT i FROM ECCartItem AS i INNER JOIN FETCH i.item AS item WHERE i.active=true AND i.userId = :userId";

		Query query = entityManagerDataSvc.getInstance().createQuery(HQLQuery);
		query.setParameter("userId", userId);
	

		@SuppressWarnings("unchecked")
		List<ECCartItem> resultList = query.getResultList();
		
		long total=0;
		
		for(ECCartItem ci: resultList) {
			total=total+ci.getQuantity();
		}
		
		return total;
	}

	@Override
	public void delete(RestRequest request, RestResponse response) throws Exception {
		if (request.containsParam(GlobalConstant.ITEMID) && !"".equals(request.getParam(GlobalConstant.ITEMID))) {
			if (request.containsParam(GlobalConstant.HARDDELETE) && !"YES".equals(request.getParam(GlobalConstant.HARDDELETE))) {
				ECCartItem cartItem = (ECCartItem) entityManagerDataSvc.getInstance().getReference(ECCartItem.class,  Long.valueOf((Integer) request.getParam(GlobalConstant.ITEMID)));
				entityManagerDataSvc.getInstance().remove(cartItem);
				
			} else {
				// soft delete cart items
				ECCartItem cartItem = entityManagerDataSvc.getInstance().find(ECCartItem.class,Long.valueOf((Integer) request.getParam(GlobalConstant.ITEMID)));
				cartItem.setActive(false);
				cartItem.setArchive(true);
				entityManagerDataSvc.getInstance().merge(cartItem);
			}
		} else {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Missing ID", response);
		}
		
		
		
	}


	@Override
	public void save(RestRequest request, RestResponse response) throws Exception {
		ECCartItem cartItem = (ECCartItem) request.getParam(GlobalConstant.ITEM);
		entityManagerDataSvc.getInstance().merge(cartItem);
		request.addParam(GlobalConstant.ITEM, cartItem);
	}


	@Override
	public void items(RestRequest request, RestResponse response) throws Exception {
		
		String queryStr = "SELECT DISTINCT x FROM ECCartItem AS x INNER JOIN FETCH x.item AS item LEFT JOIN FETCH item.attachments WHERE x.userId = :userId";
		
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			queryStr += " AND x.active =:active ";
		}
		
		// search
		ArrayList<LinkedHashMap<String,String>> searchCriteria = null;
		if (request.containsParam(GlobalConstant.SEARCHCRITERIA) && !request.getParam(GlobalConstant.SEARCHCRITERIA).equals("")) {
			if (request.getParam(GlobalConstant.SEARCHCRITERIA) instanceof Map) {
				searchCriteria = new ArrayList<>();
				searchCriteria.add((LinkedHashMap<String, String>) request.getParam(GlobalConstant.SEARCHCRITERIA));
			} else {
				searchCriteria = (ArrayList<LinkedHashMap<String, String>>) request.getParam(GlobalConstant.SEARCHCRITERIA);
			}
			
			// Loop through all the criteria
			boolean or = false;
			
			String lookupStr = "";
			for (LinkedHashMap<String,String> item : searchCriteria) {
				if (item.containsKey(GlobalConstant.SEARCHVALUE) && !"".equals(item.get(GlobalConstant.SEARCHVALUE)) && item.containsKey(GlobalConstant.SEARCHCOLUMN)) {
					if (item.get(GlobalConstant.SEARCHCOLUMN).equals("EC_STOREITEM_TABLE_TITLE")){
						if (or) { lookupStr += " OR "; }
						lookupStr += "x.name LIKE :titleValue"; 
						or = true;
					}
				}
			}
			if (!"".equals(lookupStr)) {
				queryStr += " AND ( " + lookupStr + " ) ";
			}
			
		}
		// order by
		ArrayList<LinkedHashMap<String,String>> orderCriteria = null;
		StringBuilder orderItems = new StringBuilder();
		if (request.containsParam(GlobalConstant.ORDERCRITERIA) && !request.getParam(GlobalConstant.ORDERCRITERIA).equals("")) {
			if (request.getParam(GlobalConstant.ORDERCRITERIA) instanceof Map) {
				orderCriteria = new ArrayList<>();
				orderCriteria.add((LinkedHashMap<String, String>) request.getParam(GlobalConstant.ORDERCRITERIA));
			} else {
				orderCriteria = (ArrayList<LinkedHashMap<String, String>>) request.getParam(GlobalConstant.ORDERCRITERIA);
			}
			
			// Loop through all the criteria
			boolean comma = false;
			
			for (LinkedHashMap<String,String> item : orderCriteria) {
				if (item.containsKey(GlobalConstant.ORDERCOLUMN) && item.containsKey(GlobalConstant.ORDERDIR)) {
					if (item.get(GlobalConstant.ORDERCOLUMN).equals("EC_STOREITEM_TABLE_TITLE")){
						if (comma) { orderItems.append(","); }
						orderItems.append("x.title ").append(item.get(GlobalConstant.ORDERDIR));
						comma = true;
					}
				}
			}
		}
		if (!"".equals(orderItems.toString())) {
			queryStr += " ORDER BY ".concat(orderItems.toString());
		} else {
			// default order
			queryStr += " ORDER BY x.name";
		}
		
		Query query = entityManagerDataSvc.getInstance().createQuery(queryStr);
		
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			query.setParameter("active", (Boolean) request.getParam(GlobalConstant.ACTIVE));
		} 
		
		if (searchCriteria != null){
			for (LinkedHashMap<String,String> item : searchCriteria) {
				if (item.containsKey(GlobalConstant.SEARCHVALUE) && !"".equals(item.get(GlobalConstant.SEARCHVALUE)) && item.containsKey(GlobalConstant.SEARCHCOLUMN)) {  
					if (item.get(GlobalConstant.SEARCHCOLUMN).equals("EC_STOREITEM_TABLE_TITLE")){
						query.setParameter("titleValue", "%"+((String)item.get(GlobalConstant.SEARCHVALUE)).toLowerCase()+"%");
					}
				}
			}
		}
		if (request.containsParam(GlobalConstant.LISTLIMIT) && (Integer) request.getParam(GlobalConstant.LISTLIMIT) != 0){
			query.setFirstResult((Integer) request.getParam(GlobalConstant.LISTSTART));
			query.setMaxResults((Integer) request.getParam(GlobalConstant.LISTLIMIT));
		}
		@SuppressWarnings("unchecked")
		List<ECCartItem> cartItems = query.getResultList();

		response.addParam(GlobalConstant.ITEMS, cartItems);
		
		
	}


	@Override
	public void itemCount(RestRequest request, RestResponse response) throws Exception {
		String queryStr = "SELECT COUNT(DISTINCT x) FROM ECCartItem as x WHERE x.userId = :userId";
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			queryStr += " AND x.active =:active ";
		}
		
		
		ArrayList<LinkedHashMap<String,String>> searchCriteria = null;
		if (request.containsParam(GlobalConstant.SEARCHCRITERIA) && !request.getParam(GlobalConstant.SEARCHCRITERIA).equals("")) {
			if (request.getParam(GlobalConstant.SEARCHCRITERIA) instanceof Map) {
				searchCriteria = new ArrayList<>();
				searchCriteria.add((LinkedHashMap<String, String>) request.getParam(GlobalConstant.SEARCHCRITERIA));
			} else {
				searchCriteria = (ArrayList<LinkedHashMap<String, String>>) request.getParam(GlobalConstant.SEARCHCRITERIA);
			}
			
			// Loop through all the criteria
			boolean or = false;
			
			String lookupStr = "";
			for (LinkedHashMap<String,String> item : searchCriteria) {
				if (item.containsKey(GlobalConstant.SEARCHVALUE) && !"".equals(item.get(GlobalConstant.SEARCHVALUE)) && item.containsKey(GlobalConstant.SEARCHCOLUMN)) {
					if (item.get(GlobalConstant.SEARCHCOLUMN).equals("EC_CARTITEM_TABLE_TITLE")){
						if (or) { lookupStr += " OR "; }
						lookupStr += "x.name LIKE :titleValue"; 
						or = true;
					}
				}
			}
			if (!"".equals(lookupStr)) {
				queryStr += " AND ( " + lookupStr + " ) ";
			}
			
		}

		Query query = entityManagerDataSvc.getInstance().createQuery(queryStr);
		
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			query.setParameter("active", (Boolean) request.getParam(GlobalConstant.ACTIVE));
		} 
		
		if (searchCriteria != null){
			for (LinkedHashMap<String,String> item : searchCriteria) {
				if (item.containsKey(GlobalConstant.SEARCHVALUE) && !"".equals(item.get(GlobalConstant.SEARCHVALUE)) && item.containsKey(GlobalConstant.SEARCHCOLUMN)) {  
					if (item.get(GlobalConstant.SEARCHCOLUMN).equals("EC_STOREITEM_TABLE_TITLE")){
						query.setParameter("titleValue", "%"+((String)item.get(GlobalConstant.SEARCHVALUE)).toLowerCase()+"%");
					}
				}
			}
		}
		
		Long count = (Long) query.getSingleResult();
		if (count == null){
			count = 0l;
		}
		response.addParam(GlobalConstant.ITEMCOUNT, count);
	}


	@Override
	public void item(RestRequest request, RestResponse response) throws Exception {
		if (request.containsParam(GlobalConstant.ITEMID) && !"".equals(request.getParam(GlobalConstant.ITEMID))) {
			String queryStr = "SELECT DISTINCT i FROM ECCartItem AS i INNER JOIN FETCH i.item AS item LEFT JOIN FETCH item.attachments WHERE i.active=true AND i.id = :id";
			Query query = entityManagerDataSvc.getInstance().createQuery(queryStr);
		
			query.setParameter("id", Long.valueOf((Integer) request.getParam(GlobalConstant.ITEMID)));
			ECCartItem cartItem = (ECCartItem) query.getSingleResult();
			
			response.addParam(GlobalConstant.ITEM, cartItem);
		} else {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.EXECUTIONFAILED, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_MISSING_ID",prefCacheUtil.getLang(request)), response);
		}		
	}


}
