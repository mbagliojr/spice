package com.emmbi.mobile.datacache.cache;

import android.app.Activity;

import com.emmbi.mobile.datacache.json.RequestCallback;

/**
 * Created by mbagliojr on 7/10/15.
 */
public interface CacheFetcher<T> {

    void fetchFromCache(RequestCallback<T> callback, Activity activity);
}
