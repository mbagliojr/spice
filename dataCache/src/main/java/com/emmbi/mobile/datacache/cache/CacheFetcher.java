package com.emmbi.mobile.datacache.cache;

import com.emmbi.mobile.datacache.json.RequestCallback;

/**
 * Created by mbagliojr on 7/10/15.
 */
public interface CacheFetcher<T> {

    T fetchFromCache(RequestCallback<T> callback);
}
