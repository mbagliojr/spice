package com.emmbi.mobile.spice.json;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.emmbi.mobile.spice.BaseApi;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Volley adapter for JSON requests that will be parsed into Java objects by Gson.
 */
public class CustomJsonRequest<T> extends Request<T> {
    private final Gson gson;
    private final Class<T> clazz;
    private final Type type;
    private final Map<String, String> headers;
    public final Listener<T> listener;
    private final Object postObject;
    private JsonParsingStrategy<T> jsonParsingStrategy;

    private boolean unWrap = false;

    /** Default charset for JSON request. */
    //TODO This is declared in two places. Where do we want it. The Request or the API?
    protected static final String PROTOCOL_CHARSET = "utf-8";

    /** Content type for request. */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json");


    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param type Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public CustomJsonRequest(int requestMethod, String url, Object params, Type type, Class<T> clazz, Map<String, String> headers,
                             boolean unWrap, JsonParsingStrategy<T> jsonParsingStrategy,
                             Listener<T> listener, ErrorListener errorListener) {

        super(requestMethod, url, errorListener);
        this.postObject = params;
        this.type = type;
        this.clazz = clazz;
        this.listener = listener;
        this.gson = BaseApi.getGsonInstance();
        this.unWrap = unWrap;
        this.jsonParsingStrategy = jsonParsingStrategy;

        if(headers == null) {
            this.headers = new HashMap<String, String>();
            this.headers.put("Content-type", PROTOCOL_CONTENT_TYPE);
        } else {
            this.headers = headers;
        }

    }

    public CustomJsonRequest(int requestMethod, String url, Object params, Class<T> clazz,
                             Listener<T> listener, ErrorListener errorListener) {
        this(requestMethod, url, params, null, clazz,
                listener, errorListener);
    }

    public CustomJsonRequest(int requestMethod, String url, Object params, Type type, Class<T> clazz,
                             Listener<T> listener, ErrorListener errorListener) {
        this(requestMethod, url, params, type, clazz, null, false, null,
                listener, errorListener);
    }

    public CustomJsonRequest(int requestMethod, String url, Class<T> clazz,
                             Listener<T> listener, ErrorListener errorListener) {
        this(requestMethod, url, null, clazz, listener, errorListener);
    }

    public CustomJsonRequest(int requestMethod, String url, Type type, Class<T> clazz,
                             Listener<T> listener, ErrorListener errorListener) {
        this(requestMethod, url, null, type, clazz, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
//TODO
//        if(SessionAttributes.ACCESS_TOKEN != null) {
//            String creds = String.format("%s:%s", SessionAttributes.ACCESS_TOKEN, SessionAttributes.ACCESS_TOKEN);
//            String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
//            headers.put("Authorization", auth);
//        }
        return headers;
    }

    @Override
    public byte[] getBody() {

        String json = "";
        if(postObject != null) {
            json = gson.toJson(postObject);
        }

        try {
            return json.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException e) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    json, PROTOCOL_CHARSET);
            return null;
        }
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));

            Log.v("JSON", json);

            if(jsonParsingStrategy != null) {
                T parsedResponse = jsonParsingStrategy.parseJson(json);
                return Response.success(parsedResponse, HttpHeaderParser.parseCacheHeaders(response));
            }

            if((clazz == null || clazz == String.class) && type == null) {
                return Response.success(null,HttpHeaderParser.parseCacheHeaders(response));
            }

            if(type != null) {

                T returnResponse;

                if(unWrap) {
                    Type stringStringMap = new TypeToken<Map<String, Object>>(){}.getType();
                    Map<String,Object> map = gson.fromJson(json, stringStringMap);

                    if(map != null && map.entrySet() != null && map.entrySet().size() > 0) {

                        String jsonTmp = gson.toJson(map.entrySet().iterator().next().getValue());

                        returnResponse = gson.fromJson(jsonTmp, type);
                    } else {
                        returnResponse = gson.fromJson(json, type);
                    }
                } else {
                    returnResponse = gson.fromJson(json, type);
                }

                return Response.success(
                        returnResponse, HttpHeaderParser.parseCacheHeaders(response));
            } else {
                T returnResponse;

                if(unWrap) {
                    Type stringStringMap = new TypeToken<Map<String, Object>>(){}.getType();
                    Map<String,Object> map = gson.fromJson(json, stringStringMap);

                    if(map != null && map.entrySet() != null && map.entrySet().size() > 0) {
                        String jsonTmp = gson.toJson(map.entrySet().iterator().next().getValue());

                        returnResponse = gson.fromJson(jsonTmp, clazz);
                    } else {
                        returnResponse = gson.fromJson(json, clazz);
                    }
                } else {
                    returnResponse = gson.fromJson(json, clazz);
                }

                return Response.success(
                        returnResponse, HttpHeaderParser.parseCacheHeaders(response));
            }

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}