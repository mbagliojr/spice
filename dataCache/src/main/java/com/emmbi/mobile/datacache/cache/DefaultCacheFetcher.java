package com.emmbi.mobile.datacache.cache;

import com.emmbi.mobile.datacache.json.RequestCallback;
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
        this.sugarRecordItemClass = sugarRecordItemClass;
    }

    @Override
    public T fetchFromCache(RequestCallback<T> callback) {

        //If there is no id, T has to be a List or this won't work
        if(id == null) {
            Iterator<T> iterator = (Iterator<T>) SugarRecord.findAll(sugarRecordItemClass);

            T sugarRecordObjects = (T) new ArrayList<>();

            while(iterator != null && iterator.hasNext()) {
                ((List) sugarRecordObjects).add(iterator.next());
            }

            return sugarRecordObjects;
        } else {

            T sugarRecord = (T) SugarRecord.findById(sugarRecordItemClass, id);

            return sugarRecord;
        }
    }
}
