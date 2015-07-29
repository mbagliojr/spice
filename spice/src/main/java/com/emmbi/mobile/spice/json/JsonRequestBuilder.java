package com.emmbi.mobile.spice.json;

import com.android.volley.Response;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by mbagliojr on 7/16/15.
 */
public final class JsonRequestBuilder<T> {

    private String url;
    private Class<T> clazz;
    private Type type;
    private Map<String, String> headers;
    private Response.Listener<T> listener;
    private Response.ErrorListener errorListener;
    private Object postObject;
    private int requestMethod;

    private boolean unWrap = false;

    /** Default charset for JSON request. */
    protected String PROTOCOL_CHARSET = "utf-8";

    /** Content type for request. */
    private String PROTOCOL_CONTENT_TYPE =
            String.format("application/json");

    public JsonRequestBuilder<T> setClazz(Class<T> clazz) {
        this.clazz = clazz;
        return this;
    }

    public JsonRequestBuilder<T> setType(Type type) {
        this.type = type;
        return this;
    }

    public JsonRequestBuilder<T> setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public JsonRequestBuilder<T> setSuccessListener(Response.Listener<T> listener) {
        this.listener = listener;
        return this;
    }

    public JsonRequestBuilder<T> setErrorListener(Response.ErrorListener errorListener) {
        this.errorListener = errorListener;
        return this;
    }

    public JsonRequestBuilder<T> setPostObject(Object postObject) {
        this.postObject = postObject;
        return this;
    }

    public JsonRequestBuilder<T> setUnWrap(boolean unWrap) {
        this.unWrap = unWrap;
        return this;
    }

    public JsonRequestBuilder<T> setProtocolCharset(String protocolCharset) {
        PROTOCOL_CHARSET = protocolCharset;
        return this;
    }

    public JsonRequestBuilder<T> setProtocolContentType(String protocolContentType) {
        PROTOCOL_CONTENT_TYPE = protocolContentType;
        return this;
    }

    public JsonRequestBuilder<T> setRequestMethod(int requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public JsonRequestBuilder<T> setUrl(String url) {
        this.url = url;
        return this;
    }

    public CustomJsonRequest<T> build() {

        CustomJsonRequest<T> jsonRequest = new CustomJsonRequest<T>(requestMethod, url, postObject, type, clazz, headers, unWrap,
                listener, errorListener);

        return jsonRequest;
    }
}
