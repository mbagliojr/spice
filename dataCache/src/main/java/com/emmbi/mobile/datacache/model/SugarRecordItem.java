package com.emmbi.mobile.datacache.model;

import com.orm.SugarRecord;

/**
 * Created by mbagliojr on 7/10/15.
 */
public abstract class SugarRecordItem extends SugarRecord {
    public static boolean isSugarEntity(Class<?> aClass) {
        return SugarRecordItem.class.isAssignableFrom(aClass);
    }

    public void setId(Long id) {
        super.setId(id);
    }

//    @Ignore
//    private boolean fromCache;
//
//    public boolean isFromCache() {
//        return fromCache;
//    }
//
//    public void setFromCache(boolean fromCache) {
//        this.fromCache = fromCache;
//    }

}
