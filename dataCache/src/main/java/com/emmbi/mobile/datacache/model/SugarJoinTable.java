package com.emmbi.mobile.datacache.model;

/**
 * Created by mbagliojr on 7/20/15.
 */
public class SugarJoinTable extends SugarRecordObject {

    //private

    public static boolean isJoinTable(Class<?> aClass) {
        return SugarJoinTable.class.isAssignableFrom(aClass);
    }
}
