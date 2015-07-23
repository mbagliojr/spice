package com.emmbi.mobile.datacache.cache;

import android.util.Log;

import com.emmbi.mobile.datacache.model.SugarRecordObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * Created by mbagliojr on 7/17/15.
 */
public class CacheCascader {
/*
    public static void saveCascadeChildren(SugarRecordObject object, List<String> ignoreFields) {

        Class objClass= object.getClass();


        Field[] fields = objClass.getFields();
        for(Field field: fields) {

            try {

                if(ignoreFields.contains(field.getName())) continue;

                field.setAccessible(true);

                //Explicitly invoke getter instead of grabbing value from field to be safe that we don't omit getter logic
                Object childObject = null;

                //Try get{fieldName}
                try {
                    childObject = objClass.getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), null);

                //Try is{fieldName}
                } catch (NoSuchMethodException e) {
                    try {
                        childObject = objClass.getMethod("is" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), null);

                    //No getter available. Get from field
                    } catch (NoSuchMethodException e1) {
                        childObject = field.get(object);
                    }
                }

                if(Collection.class.isAssignableFrom(childObject.getClass())) {
                    Annotation[] annotations = field.getAnnotations();

                    for (Annotation annotation : annotations) {
                        if (annotation.annotationType() == OneToMany.class) {

                            String parentFieldName = ((OneToMany)annotation).parentFieldName();
                            try {
                                Field setParentField = childObject.getClass().getField(parentFieldName);
                                setParentField.setAccessible(true);
                                setParentField.set(object, ((SugarRecordObject) childObject).getId());
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            }
                            break;
                        } else if (annotation.annotationType() == ManyToMany.class) {

                            //TODO
                            String joinTableClassName = ((ManyToMany)annotation).joinTable();

                            break;
                        }
                    }

                    for (Object listItem : (Collection) childObject) {

                        if (listItem == null) continue;

                        if (SugarRecordObject.isSugarEntity(listItem.getClass())) {
                            SugarRecordObject.save(listItem);
                            saveCascadeChildren((SugarRecordObject)listItem, ignoreFields);
                        }
                    }
                } else if(SugarRecordObject.class.isAssignableFrom(childObject.getClass())) {

                    Annotation[] annotations = field.getAnnotations();

                    for (Annotation annotation : annotations) {
                        if (annotation.annotationType() == ManyToOne.class) {
                            String childFieldName = ((ManyToOne)annotation).name();
                            try {
                                Field setChildField = objClass.getField(childFieldName);
                                setChildField.setAccessible(true);
                                setChildField.set(object, ((SugarRecordObject) childObject).getId());
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            }
                            break;

                        //Make it one-directional, recursion will add bidirectional if necessary
                        } else if (annotation.annotationType() == OneToOne.class) {

                            String childFieldName = ((OneToOne)annotation).name();
                            try {
                                Field setChildField = objClass.getField(childFieldName);
                                setChildField.setAccessible(true);
                                setChildField.set(object, ((SugarRecordObject) childObject).getId());
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            }

                            break;
                        }

                    }

                    SugarRecordObject.saveInTx(object, childObject);
                    saveCascadeChildren((SugarRecordObject)childObject, ignoreFields);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    /*
    public static void fetchCascadeChildren(Object object, List<String> ignoreMethods) {

        Class objClass= object.getClass();

        // Get the public methods associated with this class.
        Method[] methods = objClass.getMethods();
        Method getCascadedObjects = objClass.getMethod("getCascadedObjects", null);

        List<Class<? extends SugarRecordObject>> cascadedObjects = getCascadedObjects.invoke(object, null);

        for(Class<? extends SugarRecordObject> objectsToCascadeClass: cascadedObjects) {
            SugarRecordObject.findAll()

        }

        for (Method method:methods)
        {
            if(method.getName().startsWith("set") && !ignoreMethods.contains(method.getName())) {
                try {

                    method.get

                    Object returnedObj = method.invoke(object, null);

                    if(returnedObj == null) continue;

                    if(Collection.class.isAssignableFrom(returnedObj.getClass())) {
                        for(Object listItem: (Collection) returnedObj) {

                            if(listItem == null) continue;

                            if(SugarRecordObject.isSugarEntity(listItem.getClass())) {

                                SugarRecordObject.save(listItem);
                                saveCascadeChildren(listItem, ignoreMethods);
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
    }*/
}
