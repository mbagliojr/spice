package com.emmbi.mobile.datacache.cache;

import com.emmbi.mobile.datacache.json.RequestCallback;
import com.emmbi.mobile.datacache.model.SugarRecordObject;

/**
 * Created by mbagliojr on 7/10/15.
 */
public interface CacheFetcher<T extends SugarRecordObject> {

    T fetchFromCache(RequestCallback<T> callback);
}
