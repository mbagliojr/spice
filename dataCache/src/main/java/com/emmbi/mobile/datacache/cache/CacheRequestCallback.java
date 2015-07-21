package com.emmbi.mobile.datacache.cache;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.emmbi.mobile.datacache.json.RequestCallback;
import com.emmbi.mobile.datacache.model.SugarRecordObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mbagliojr on 4/14/15.
 */
public abstract class CacheRequestCallback<T extends SugarRecordObject> extends RequestCallback<T> {

    private List<String> ignoreMethods = new ArrayList<String>();

    private CacheFetcher<T> cacheFetcher;

    public CacheRequestCallback(CacheFetcher<T> cacheFetcher, Context context) {
        super(context);

        this.cacheFetcher = cacheFetcher;
        ignoreMethods.add("isSugarEntity");
    }

    public void updateUI(T response) {
        super.updateUI(response);

    }

    public void fetchFromCacheAndUpdateUI() {
        if(cacheFetcher != null) {
            try {
                T response = cacheFetcher.fetchFromCache(this);
                updateUI(response);
            } catch (Exception e) {
            }
        }
    }

    public void onServerFailure(VolleyError error) {
        super.onServerFailure(error);
    }

    @Override
    public Response.Listener<T> getSuccessListener() {
        return new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {

                response.save();
                CacheCascader.saveCascadeChildren(response, ignoreMethods);

                fetchFromCacheAndUpdateUI();
//        else if(SugarRecordList.class.isAssignableFrom(response.getClass())) {
//            SugarRecordList sugarRecordList = (SugarRecordList) response;
//
//            if(sugarRecordList.getRecords() != null) {
//                for (Object object : sugarRecordList.getRecords()) {
//                    if(SugarRecord.class.isAssignableFrom(object.getClass())) {
//                        ((SugarRecord) object).save();
//                    }
//                }
//            }
//        }
            }
        };
    }

}
