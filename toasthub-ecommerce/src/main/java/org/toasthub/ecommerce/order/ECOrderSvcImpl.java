/*
 * Copyright (C) 2023 The ToastHub Project
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;


@Service("ECOrderSvc")
public class ECOrderSvcImpl implements ECOrderSvc {

	@Autowired
	ECOrderDao orderDao;

	@Autowired
	Environment env;

	


	@Override
	public void pendingList(RestRequest request, RestResponse response) {
		int listLimit = 20;
	/*	if (request.getParam("PENDING_listLimit") != null) {
			response.addParam("PENDING_listLimit", request.getParam("PENDING_listLimit"));
			listLimit = (int) request.getParam("PENDING_listLimit");
		} else if (request.getListLimit() != null) {
			response.setListLimit(request.getListLimit());
			listLimit = request.getListLimit();
		} else {
			response.setListLimit(listLimit);
		}
		int listStart = 0;
		if (request.getParam("PENDING_listStart") != null) {
			response.addParam("PENDING_listStart", request.getParam("PENDING_listStart"));
			listStart = (int) request.getParam("PENDING_listStart");
		} else if (request.getListStart() != null) {
			response.setListStart(request.getListStart());
			listStart = request.getListStart();
		} else {
			response.setListStart(listStart);
		}
		try {
			Long count = purchaseDao.pendingCount(request.getOrderCriteria(),request.getSearchCriteria());
			if (count > 0) {
				List<PurchaseRequest> list = purchaseDao.pendingList(request.getOrderCriteria(),request.getSearchCriteria(),listStart,listLimit);
				if (listStart == listLimit && list.size() == 0) {
					listStart = listStart - listLimit;
					list = purchaseDao.pendingList(request.getOrderCriteria(),request.getSearchCriteria(),listStart,listLimit);
					response.setListStart(listStart);
				}
				response.addParam("PENDING_ITEMS", list);
			}
			response.addParam("PENDING_COUNT", count);
			response.setStatus(GlobalConstants.SUCCESSFUL);
		} catch (Exception e) {
			response.setStatus(GlobalConstants.ERROR);
			Utils.addError(response, e.getMessage());
			e.printStackTrace();
		}*/
	}

	@Override
	public void completedList(RestRequest request, RestResponse response) {
		int listLimit = 20;
	/*	if (request.getParam("COMPLETED_listLimit") != null) {
			response.addParam("COMPLETED_listLimit", request.getParam("COMPLETED_listLimit"));
			listLimit = (int) request.getParam("COMPLETED_listLimit");
		} else if (request.getListLimit() != null) {
			response.setListLimit(request.getListLimit());
			listLimit = request.getListLimit();
		} else {
			response.setListLimit(listLimit);
		}
		int listStart = 0;
		if (request.getParam("COMPLETED_listStart") != null) {
			response.addParam("COMPLETED_listStart", request.getParam("COMPLETED_listStart"));
			listStart = (int) request.getParam("COMPLETED_listStart");
		} else if (request.getListStart() != null) {
			response.setListStart(request.getListStart());
			listStart = request.getListStart();
		} else {
			response.setListStart(listStart);
		}
		try {
			Long count = purchaseDao.completedCount(request.getOrderCriteria(),request.getSearchCriteria());
			if (count > 0) {
				List<PurchaseRequest> list = purchaseDao.completedList(request.getOrderCriteria(),request.getSearchCriteria(),listStart,listLimit);
				response.addParam("COMPLETED_ITEMS", list);
			}
			response.addParam("COMPLETED_COUNT", count);
			response.setStatus(GlobalConstants.SUCCESSFUL);
		} catch (Exception e) {
			response.setStatus(GlobalConstants.ERROR);
			Utils.addError(response, e.getMessage());
			e.printStackTrace();
		}*/
	}
	
	@Override
	public void pendingStats(RestRequest request, RestResponse response) {
		/*try {
			Long stats = purchaseDao.pendingStats();
			response.addParam("PENDING_STATS", stats);
		} catch (Exception e) {
			response.setStatus(GlobalConstants.ERROR);
			Utils.addError(response, e.getMessage());
			e.printStackTrace();
		}*/
		
	}

	@Override
	public void completedStats(RestRequest request, RestResponse response) {
	/*	try {
			List<Map<String,Long>> stats = purchaseDao.completedStats();
			response.addParam("COMPLETED_STATS", stats);
		} catch (Exception e) {
			response.setStatus(GlobalConstants.ERROR);
			Utils.addError(response, e.getMessage());
			e.printStackTrace();
		}*/
	}

	@Override
	public void process(RestRequest request, RestResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemCount(RestRequest request, RestResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void item(RestRequest request, RestResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void items(RestRequest request, RestResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(RestRequest request, RestResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(RestRequest request, RestResponse response) {
		// TODO Auto-generated method stub
		
	}

	

}
