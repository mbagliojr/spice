package com.emmbi.mobile.datacache.model;

import com.orm.SugarRecord;

/**
 * Created by mbagliojr on 7/10/15.
 */
public abstract class SugarRecordObject extends SugarRecord {

    public static boolean isSugarEntity(Class<?> aClass) {
        return SugarRecordObject.class.isAssignableFrom(aClass);
    }

//    private Long parentId;
//
//    public Long getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(Long parentId) {
//        this.parentId = parentId;
//    }
//
//    @Ignore
//    private List<Class<? extends SugarRecordObject>> cascadedObjects;
//
//    public void setCascadedObjects(List<Class<? extends SugarRecordObject>> cascadedObjects) {
//        this.cascadedObjects = cascadedObjects;
//    }
//
//    public void addCascadedObject(Class<? extends SugarRecordObject> cascadedObject) {
//        if(this.cascadedObjects == null) {
//            this.cascadedObjects = new ArrayList<Class<? extends SugarRecordObject>>();
//        }
//
//        this.cascadedObjects.add(cascadedObject);
//    }
//
//    public List<Class<? extends SugarRecordObject>> getCascadedObjects() {
//        return cascadedObjects;
//    }
}
