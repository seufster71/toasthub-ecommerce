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

package org.toasthub.ecommerce.storeItem;

import org.toasthub.ecommerce.model.ECAttachment;
import org.toasthub.ecommerce.model.ECAttachmentMeta;
import org.toasthub.ecommerce.model.ECBaseDao;

public interface ECStoreItemDao extends ECBaseDao {
	
    public ECAttachment getAttachment(ECAttachmentMeta attachmentMeta) throws Exception;
    public ECAttachmentMeta getAttachment(Long id) throws Exception;
    public void deleteAttachment(Long id) throws Exception;
    public ECAttachmentMeta saveAttachment(ECAttachmentMeta attachmentMeta) throws Exception;
    
    public int getInventory(Long storeItemId);
}
