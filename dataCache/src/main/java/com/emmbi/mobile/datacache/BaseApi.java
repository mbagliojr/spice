package com.emmbi.mobile.datacache;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.kickserv.ksa.api.util.BooleanSerializer;
//import com.kickserv.ksa.api.util.CustomJsonRequest;
//import com.kickserv.ksa.api.util.DateDeserializer;


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

            //BooleanSerializer booleanSerializer = new BooleanSerializer();

            gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                    .registerTypeAdapter(Date.class, new DateDeserializer())
//                    .registerTypeAdapter(Boolean.class, booleanSerializer)
//                    .registerTypeAdapter(boolean.class, booleanSerializer)
                    .create();
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
