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

import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.ecommerce.model.BaseSvc;

public interface PurchaseSvc extends BaseSvc {
	public void approve(RestRequest request,RestResponse response);
	public void deny(RestRequest request,RestResponse response);
	public void pendingList(RestRequest request,RestResponse response);
	public void completedList(RestRequest request,RestResponse response);
	
	public void addPurchaseRequest(RestRequest request, RestResponse response);
	public void pendingStats(RestRequest request, RestResponse response);
	public void completedStats(RestRequest request, RestResponse response);
	
	public void memberPendingList(RestRequest request,RestResponse response);
	public void memberCompletedList(RestRequest request,RestResponse response);
}
