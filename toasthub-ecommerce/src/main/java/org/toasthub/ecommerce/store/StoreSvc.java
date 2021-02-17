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

package org.toasthub.ecommerce.store;

import org.springframework.web.multipart.MultipartFile;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.ecommerce.model.AttachmentMeta;
import org.toasthub.ecommerce.model.BaseSvc;

public interface StoreSvc extends BaseSvc {

	public void attachmentList(RestRequest request, RestResponse response);
	public void deleteAttachment(RestRequest request,RestResponse response);
	public void upload(Long jobId, MultipartFile file, RestResponse restResponse);
	public AttachmentMeta getAttachment(String fileId);
}
