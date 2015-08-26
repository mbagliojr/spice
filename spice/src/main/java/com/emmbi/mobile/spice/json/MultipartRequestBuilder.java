package com.emmbi.mobile.spice.json;

import com.android.volley.Response;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by mbagliojr on 7/16/15.
 */
public final class MultipartRequestBuilder<T> {

    private String url;
    private Class<T> clazz;
    private Type type;
    private Map<String, String> headers;
    private Map<String, String> params;
    private Response.Listener<T> listener;
    private Response.ErrorListener errorListener;
    private byte[] byteArray;
    private int requestMethod;
    private JsonParsingStrategy<T> jsonParsingStrategy;

    private boolean unWrap = false;

    /** Default charset for JSON request. */
    protected String PROTOCOL_CHARSET = "utf-8";

    /** Content type for request. */
    private String PROTOCOL_CONTENT_TYPE =
            String.format("application/json");

    public MultipartRequestBuilder<T> setClazz(Class<T> clazz) {
        this.clazz = clazz;
        return this;
    }

    public MultipartRequestBuilder<T> setType(Type type) {
        this.type = type;
        return this;
    }

    public MultipartRequestBuilder<T> setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public MultipartRequestBuilder<T> setSuccessListener(Response.Listener<T> listener) {
        this.listener = listener;
        return this;
    }

    public MultipartRequestBuilder<T> setErrorListener(Response.ErrorListener errorListener) {
        this.errorListener = errorListener;
        return this;
    }

    public MultipartRequestBuilder<T> setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public MultipartRequestBuilder<T> setUnWrap(boolean unWrap) {
        this.unWrap = unWrap;
        return this;
    }

    public MultipartRequestBuilder<T> setProtocolCharset(String protocolCharset) {
        PROTOCOL_CHARSET = protocolCharset;
        return this;
    }

    public MultipartRequestBuilder<T> setProtocolContentType(String protocolContentType) {
        PROTOCOL_CONTENT_TYPE = protocolContentType;
        return this;
    }

    public MultipartRequestBuilder<T> setRequestMethod(int requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public MultipartRequestBuilder<T> setUrl(String url) {
        this.url = url;
        return this;
    }

    public MultipartRequestBuilder<T> setJsonParsingStrategy(JsonParsingStrategy<T> jsonParsingStrategy) {
        this.jsonParsingStrategy = jsonParsingStrategy;
        return this;
    }

    public MultipartRequestBuilder<T> setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
        return this;
    }

    public CustomMultipartRequest<T> build() {

        CustomMultipartRequest<T> jsonRequest = new CustomMultipartRequest<T>(requestMethod, url, params, type, headers, byteArray, clazz, unWrap,
                jsonParsingStrategy,listener, errorListener);

        return jsonRequest;
    }
}
