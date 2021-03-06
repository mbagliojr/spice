package com.emmbi.mobile.spice.cache;

import android.app.Activity;

import com.emmbi.mobile.spice.json.RequestCallback;

/**
 * Created by mbagliojr on 7/10/15.
 */
public interface CacheFetcher<T> {

    void fetchFromCache(CacheRequestCallback<T> callback, Activity activity);
}
