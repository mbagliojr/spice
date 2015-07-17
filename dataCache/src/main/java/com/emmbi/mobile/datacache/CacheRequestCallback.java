package com.emmbi.mobile.datacache;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.emmbi.mobile.datacache.model.SugarRecordItem;
import com.orm.SugarRecord;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mbagliojr on 4/14/15.
 */
public abstract class CacheRequestCallback<T> extends RequestCallback<T> {

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

    private void cascadeChildren(Object object) {

        Class objClass= object.getClass();

        // Get the public methods associated with this class.
        Method[] methods = objClass.getMethods();
        for (Method method:methods)
        {
            if(method.getName().startsWith("get") || method.getName().startsWith("is") && !ignoreMethods.contains(method.getName())) {
                try {
                    Object returnedObj = method.invoke(object, null);

                    if(returnedObj == null) continue;

                    if(Collection.class.isAssignableFrom(returnedObj.getClass())) {
                        for(Object listItem: (Collection) returnedObj) {

                            if(listItem == null) continue;

                            if(SugarRecordItem.isSugarEntity(listItem.getClass())) {

                                SugarRecordItem.save(listItem);
                                cascadeChildren(listItem);
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    Log.e("ERROR", method.getName() + " " + e.getMessage());
                }
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

                if(SugarRecordItem.isSugarEntity(response.getClass())) {
                    ((SugarRecordItem) response).save();

                    cascadeChildren(response);
                }

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
