package com.emmbi.mobile.spice;

import com.emmbi.mobile.spice.cache.CacheRequestCallback;
import com.emmbi.mobile.spice.json.CustomJsonRequest;
import com.emmbi.mobile.spice.json.RequestCallback;
import com.google.gson.Gson;

/**
 * Created by mbagliojr on 4/14/15.
 */
public class BaseApi {

    private static Gson gson;

    /**
     * It is recommended to extend the ApplicationController and set the GSON in the constructor.
     * @param g
     */
    public static void setGson(Gson g) {
        gson = g;
    }

    public static Gson getGsonInstance() {

        if(gson == null) {
            gson = new Gson();
        }

        return gson;
    }

    protected static <T> void addToRequestQueue(CustomJsonRequest<T> req, RequestCallback callback) {

        if(callback instanceof CacheRequestCallback) {
            ((CacheRequestCallback) callback).fetchFromCacheAndUpdateUI();
        }
        ApplicationController.getInstance().addToRequestQueue(req);
    }
}
