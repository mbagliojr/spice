package com.emmbi.mobile.datacache.model;

import com.orm.SugarRecord;

/**
 * Created by mbagliojr on 7/20/15.
 */
public class SugarJoinTable extends SugarRecord {

    //private

    public static boolean isJoinTable(Class<?> aClass) {
        return SugarJoinTable.class.isAssignableFrom(aClass);
    }
}
