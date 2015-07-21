package com.emmbi.mobile.datacache.cache;

import com.emmbi.mobile.datacache.json.RequestCallback;
import com.emmbi.mobile.datacache.model.SugarRecordObject;

/**
 * Created by mbagliojr on 7/17/15.
 */
public class DefaultCacheFetcher<T extends SugarRecordObject> implements CacheFetcher<T> {

    private Class<? extends SugarRecordObject> sugarRecordItemClass;
    private Long id;
    private String sortColumn;
    private Integer pageSize;
    private Integer pageNum;

//    public DefaultCacheFetcher(Class<? extends SugarRecordObject> sugarRecordItemClass, Long id, String sortColumn, Integer pageSize, Integer pageNum) {
//        this.sugarRecordItemClass = sugarRecordItemClass;
//        this.id = id;
//        this.sortColumn = sortColumn;
//        this.pageSize = pageSize;
//        this.pageNum = pageNum;
//    }

    public DefaultCacheFetcher(Class<? extends SugarRecordObject> sugarRecordItemClass, Long id) {
        this.sugarRecordItemClass = sugarRecordItemClass;
        this.id = id;
        this.sortColumn = sortColumn;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    @Override
    public T fetchFromCache(RequestCallback<T> callback) {

//        Integer limit = pageSize;
//        limit *= pageNum == null ? 1: pageNum;

        T sugarRecord = (T) SugarRecordObject.findById(sugarRecordItemClass, id);

        return sugarRecord;
    }
}
