package org.toasthub.ecommerce.purchase;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.ecommerce.cart.ECCartDao;
import org.toasthub.ecommerce.cart.ECCartSvc;
import org.toasthub.ecommerce.store.ECStoreSvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service("ECPurchaseSvc")
public class ECPurchaseSvcImpl implements ECPurchaseSvc {

	@Autowired
	ECPurchaseDao purchaseDao;

	
	@Autowired
	Environment env;
	
	@Autowired
	TaskExecutor executor;

	@Autowired
	ECCartSvc cartSvc;
	
	@Autowired
	ECCartDao cartDao;

	@Autowired
	ECStoreSvc storeSvc;

	@Override
	public void approve(RestRequest request, RestResponse response) {
	/*	if (request.containsParam("PURCHASE_ITEM_ID")) {
			try {
				PurchaseRequest req = purchaseDao.approve(Long.valueOf((int) request.getParam("PURCHASE_ITEM_ID")));
				response.setStatus(GlobalConstants.SUCCESSFUL);

			} catch (Exception e) {
				response.setStatus(GlobalConstants.ERROR);
				Utils.addError(response, "Unable to approve item");
				Utils.addError(response, e.getMessage());
			}
		} else {
			response.setStatus(GlobalConstants.ERROR);
			Utils.addError(response, "Missing purchase id");
		}*/
	}

	@Override
	public void deny(RestRequest request, RestResponse response) {
		/*if (request.containsParam("PURCHASE_ITEM_ID")) {
			try {
				PurchaseRequest req = purchaseDao.deny(Long.valueOf((int) request.getParam("PURCHASE_ITEM_ID")));
				String denyReason = (String) request.getParam("DENY_REASON");
				response.setStatus(GlobalConstants.SUCCESSFUL);
				MailSvcImpl mailSvcImpl = new MailSvcImpl();
				mailSvcImpl.setParams(env,emailSender,req.getRequester().getEmail(),"Your purchase has been declined","Your purchase cannot be fulfilled at this time. Please make another selection.",true,denyReason,MailSvcImpl.BANNER_BLANK);
				executor.execute(mailSvcImpl);
			} catch (Exception e) {
				response.setStatus(GlobalConstants.ERROR);
				Utils.addError(response, "Unable to deny item");
				Utils.addError(response, e.getMessage());
			}
		} else {
			response.setStatus(GlobalConstants.ERROR);
			Utils.addError(response, "Missing purchase id");
		}*/
	}

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
	public void addPurchaseRequest(RestRequest request, RestResponse response) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
	/*	try {
			PepUser pepUser = pointsDao.getUser(username);
			if(pepUser.getUserPoints() > 0) {
				if(request.containsParam("PURCHASE_REQUEST")) {
					ObjectMapper mapper = new ObjectMapper();
					mapper.findAndRegisterModules();
					PurchaseRequest[] purchaseRequests = mapper.convertValue(request.getParam("PURCHASE_REQUEST"), PurchaseRequest[].class);
					
					List<String> items = purchaseDao.processPurchaseRequest(purchaseRequests, response, pepUser);
					// send to user
					MailSvcImpl mailSvcImpl = new MailSvcImpl();
					mailSvcImpl.setParams(env,emailSender,pepUser.getEmail(),"Thank you for your PEP points store purchase.","Thank you for your order!",true,"",MailSvcImpl.BANNER_THANKYOU);
					executor.execute(mailSvcImpl);
					// send to storeAdmins
					List<PepUser> storeAdmins = userDao.getStoreAdmins();
					for (PepUser admin : storeAdmins) {
						String name = pepUser.getFirstName() +" "+ pepUser.getLastName();
						MailSvcImpl mailSvc = new MailSvcImpl();
						mailSvc.setParams(env,emailSender,admin.getEmail(), name + " made a purchase from the PEP points store.",name + " has purchased " + items.toString() + ". Please retrieve and deliver.",true,"",null);
						executor.execute(mailSvc);
					}
					
				} else {
					response.setStatus(GlobalConstants.ERROR);
					Utils.addError(response, "You do not have enough points");
				}		
			}
		} catch (Exception e) {
			response.setStatus(GlobalConstants.ERROR);
			Utils.addError(response, "Unable to add purchase request");
			//Utils.addError(response, e.getMessage());
			e.printStackTrace();
		}*/

	}

	@Override
	public void memberPendingList(RestRequest request, RestResponse response) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
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
			listStart =  (int) request.getParam("PENDING_listStart");
		} else if (request.getListStart() != null) {
			response.setListStart(request.getListStart());
			listStart = request.getListStart();
		} else {
			response.setListStart(listStart);
		}
		
		try {
			PepUser pepUser = pointsDao.getUser(username);
			Long count = purchaseDao.memberPendingCount(pepUser,request.getOrderCriteria(),request.getSearchCriteria());
			if (count > 0) {
				List<PurchaseRequest> list = purchaseDao.memberPendingList(pepUser,request.getOrderCriteria(),request.getSearchCriteria(),listStart,listLimit);
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
	public void memberCompletedList(RestRequest request, RestResponse response) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
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
			listStart =  (int) request.getParam("COMPLETED_listStart");
		} else if (request.getListStart() != null) {
			response.setListStart(request.getListStart());
			listStart = request.getListStart();
		} else {
			response.setListStart(listStart);
		}
		try {
			PepUser pepUser = pointsDao.getUser(username);
			Long count = purchaseDao.memberCompletedCount(pepUser,request.getOrderCriteria(),request.getSearchCriteria());
			if (count > 0) {
				List<PurchaseRequest> list = purchaseDao.memberCompletedList(pepUser,request.getOrderCriteria(),request.getSearchCriteria(),listStart,listLimit);
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
