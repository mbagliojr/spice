package com.emmbi.mobile.spice.json;

/**
 * Created by mbagliojr on 8/11/15.
 */
public abstract class JsonParsingStrategy<T> {

    public abstract T parseJson(String json);
}
