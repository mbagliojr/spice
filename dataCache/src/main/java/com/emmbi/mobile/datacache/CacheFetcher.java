package com.emmbi.mobile.datacache;

/**
 * Created by mbagliojr on 7/10/15.
 */
public interface CacheFetcher<T> {

    T fetchFromCache(RequestCallback<T> callback);
}
