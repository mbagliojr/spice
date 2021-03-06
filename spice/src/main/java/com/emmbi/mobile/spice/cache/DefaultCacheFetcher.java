package com.emmbi.mobile.spice.cache;

import android.app.Activity;
import android.util.Log;

import com.emmbi.mobile.spice.json.RequestCallback;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mbagliojr on 7/17/15.
 */
public class DefaultCacheFetcher<T> implements CacheFetcher<T> {

    private Class<? extends SugarRecord> sugarRecordItemClass;
    private Long id;

    public DefaultCacheFetcher(Class<? extends SugarRecord> sugarRecordItemClass, Long id) {
        this.sugarRecordItemClass = sugarRecordItemClass;
        this.id = id;
    }

    public DefaultCacheFetcher(Class<? extends SugarRecord> sugarRecordItemClass) {
        this(sugarRecordItemClass, null);
    }

    @Override
    public void fetchFromCache(final CacheRequestCallback<T> callback, final Activity activity) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //If there is no id, T has to be a List or this won't work
                    if (id == null) {
                        Iterator<T> iterator = (Iterator<T>) SugarRecord.findAll(sugarRecordItemClass);

                        final T sugarRecordObjects = (T) new ArrayList<>();

                        while (iterator != null && iterator.hasNext()) {
                            ((List) sugarRecordObjects).add(iterator.next());
                        }

                        if(activity != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    callback.updateUI(sugarRecordObjects, callback.isFromCache());
                                }
                            });
                        }



                    } else {

                        final T sugarRecord = (T) SugarRecord.findById(sugarRecordItemClass, id);

                        if(activity != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    callback.updateUI(sugarRecord, callback.isFromCache());
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    Log.e("Spice", e.getMessage());
                }
            }
        }) ;

        thread.start();
    }
}
