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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.toasthub.core.common.UtilSvc;
import org.toasthub.core.general.model.GlobalConstant;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.core.preference.model.PrefCacheUtil;
import org.toasthub.ecommerce.member.ECMemberDao;
import org.toasthub.ecommerce.model.ECAttachmentMeta;
import org.toasthub.ecommerce.model.ECCartItem;
import org.toasthub.ecommerce.model.ECConstant;
import org.toasthub.ecommerce.model.ECMember;
import org.toasthub.ecommerce.storeItem.ECStoreItemDao;
import org.toasthub.security.model.MyUserPrincipal;
import org.toasthub.security.model.User;


@Service("ECCartSvc")
public class ECCartSvcImpl implements ECCartSvc {

	@Autowired
	@Qualifier("ECCartDao")
	ECCartDao cartDao;
	
	@Autowired
	@Qualifier("ECStoreItemDao")
	ECStoreItemDao storeItemDao;
	
	@Autowired
	@Qualifier("ECMemberDao")
	ECMemberDao memberDao;
	
	@Autowired
	UtilSvc utilSvc;
	
	@Autowired
	PrefCacheUtil prefCacheUtil;

	@Override
	public void process(RestRequest request, RestResponse response) {
		String action = (String) request.getParams().get(GlobalConstant.ACTION);
		List<String> global =  new ArrayList<String>(Arrays.asList("LANGUAGES"));
		
		Long count = 0l;
		switch (action) {
		case "INIT":
			request.addParam(PrefCacheUtil.PREFPARAMLOC, PrefCacheUtil.RESPONSE);
			prefCacheUtil.getPrefInfo(request,response);
			this.itemCount(request, response);
			count = (Long) response.getParam(GlobalConstant.ITEMCOUNT);
			if (count != null && count > 0){
				this.items(request, response);
			}
			response.addParam(GlobalConstant.ITEMNAME, request.getParam(GlobalConstant.ITEMNAME));
			break;
		case "LIST":
			request.addParam(PrefCacheUtil.PREFPARAMLOC, PrefCacheUtil.RESPONSE);
			prefCacheUtil.getPrefInfo(request,response);
			this.itemCount(request, response);
			count = (Long) response.getParam(GlobalConstant.ITEMCOUNT);
			if (count != null && count > 0){
				this.items(request, response);
			}
			response.addParam(GlobalConstant.ITEMNAME, request.getParam(GlobalConstant.ITEMNAME));
			break;
		case "ITEM":
			request.addParam(PrefCacheUtil.PREFPARAMLOC, PrefCacheUtil.RESPONSE);
			prefCacheUtil.getPrefInfo(request,response);
			this.item(request, response);
			break;
		case "DELETE":
			this.delete(request, response);
			break;
		case "SAVE":
			if (!request.containsParam(PrefCacheUtil.PREFFORMKEYS)) {
				List<String> forms =  new ArrayList<String>(Arrays.asList("PM_PRODUCT_FORM"));
				request.addParam(PrefCacheUtil.PREFFORMKEYS, forms);
			}
			request.addParam(PrefCacheUtil.PREFGLOBAL, global);
			prefCacheUtil.getPrefInfo(request,response);
			this.save(request, response);
			break;
		default:
			utilSvc.addStatus(RestResponse.INFO, RestResponse.ACTIONNOTEXIST, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_ACTION_NOT_AVAIL",prefCacheUtil.getLang(request)), response);
			break;
		}
	}

	@Override
	public void attachmentList(RestRequest request, RestResponse response) {
		/*
		 * try { List<AttachmentMeta> attachmentList =
		 * storeDao.attachmentList(request.getId());
		 * response.setAttachmentList(attachmentList); } catch (Exception e) {
		 * response.setStatus(GlobalConstants.ERROR); if (response.getErrors() == null)
		 * { response.setErrors(new ArrayList<String>()); }
		 * response.getErrors().add("Error getting attachments " + e.getMessage());
		 * e.printStackTrace(); }
		 */
	}

	@Override
	public ECAttachmentMeta getAttachment(String fileId) {
		try {
			Long id = Long.parseLong(fileId);
			return cartDao.getAttachment(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void itemCount(RestRequest request, RestResponse response) {
		try {
			User user = ((MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
			ECMember member = memberDao.getMember(user);
			request.addParam(ECConstant.MEMBER, member);
			cartDao.itemCount(request, response);
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.EXECUTIONFAILED, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_EXECUTION_FAIL",prefCacheUtil.getLang(request)), response);
			e.printStackTrace();
		}
	}

	@Override
	public void item(RestRequest request, RestResponse response) {
		try {
			cartDao.item(request, response);
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.EXECUTIONFAILED, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_EXECUTION_FAIL",prefCacheUtil.getLang(request)), response);
			e.printStackTrace();
		}
	}

	@Override
	public void items(RestRequest request, RestResponse response) {
		try {
			User user = ((MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
			ECMember member = memberDao.getMember(user);
			request.addParam(ECConstant.MEMBER, member);
		
			cartDao.items(request, response);
			// check for available quantity
			List<ECCartItem>	list = (List<ECCartItem>) response.getParam(GlobalConstant.ITEMS);
			for(ECCartItem item : list) {
				item.setAvailableQuantity(storeItemDao.getQuantity(item.getItem().getId()));
			}
			
			if (response.getParam("items") == null){
				utilSvc.addStatus(RestResponse.INFO, RestResponse.EMPTY, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_NO_ITEMS",prefCacheUtil.getLang(request)), response);
			}
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.EXECUTIONFAILED, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_EXECUTION_FAIL",prefCacheUtil.getLang(request)), response);
			e.printStackTrace();
		}
	}

	@Override
	public void save(RestRequest request, RestResponse response) {
			
		try {
			// validate
			utilSvc.validateParams(request, response);
			
			if ((Boolean) request.getParam(GlobalConstant.VALID) == false) {
				utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_VALIDATION_ERR",prefCacheUtil.getLang(request)), response);
				return;
			}
			User user = ((MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
			ECMember member = memberDao.getMember(user);
			request.addParam(ECConstant.MEMBER, member);
			
			// get existing item
			Map<String,Object> inputList = (Map<String, Object>) request.getParam(GlobalConstant.INPUTFIELDS);
			if (inputList.containsKey(GlobalConstant.ITEMID) && inputList.get(GlobalConstant.ITEMID) != null && !"".equals(inputList.get(GlobalConstant.ITEMID))) {
				request.addParam(GlobalConstant.ITEMID, inputList.get(GlobalConstant.ITEMID));
				cartDao.item(request, response);
				request.addParam(GlobalConstant.ITEM, response.getParam(GlobalConstant.ITEM));
				response.getParams().remove(GlobalConstant.ITEM);
			} else {
				ECCartItem cartItem = new ECCartItem();
				cartItem.setUserId(user.getId());
				//cartItem.setItem(storeItem);
				cartItem.setQuantity(1);
				//cartItem.setTotalCost(storeItem.getPrice());
				cartItem.setActive(true);
				cartItem.setArchive(false);
				cartItem.setLocked(false);
				request.addParam(GlobalConstant.ITEM, cartItem);
			}
			// marshall
			utilSvc.marshallFields(request, response);
			
			// save
			cartDao.save(request, response);
			
			utilSvc.addStatus(RestResponse.INFO, RestResponse.SUCCESS, prefCacheUtil.getPrefText( "GLOBAL_SERVICE", "GLOBAL_SERVICE_SAVE_SUCCESS", prefCacheUtil.getLang(request)), response);
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, prefCacheUtil.getPrefText( "GLOBAL_SERVICE", "GLOBAL_SERVICE_SAVE_FAIL", prefCacheUtil.getLang(request)), response);
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(RestRequest request, RestResponse response) {
		try {
			cartDao.delete(request, response);
			utilSvc.addStatus(RestResponse.INFO, RestResponse.SUCCESS, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_DELETE_SUCCESS",prefCacheUtil.getLang(request)), response);
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_DELETE_FAIL",prefCacheUtil.getLang(request)), response);
			e.printStackTrace();
		}
	}
		
	
}
