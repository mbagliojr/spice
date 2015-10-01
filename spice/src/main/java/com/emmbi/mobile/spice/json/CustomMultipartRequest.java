package com.emmbi.mobile.spice.json;

import android.util.Base64;
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

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Volley adapter for JSON requests that will be parsed into Java objects by Gson.
 */
public class CustomMultipartRequest<T> extends Request<T> {
    private final Gson gson;
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Listener<T> listener;
    private final Map<String, String> params;
    private final byte[] byteArray;
    private final Type type;

    private boolean unWrap = false;

    private HttpEntity httpentity;
    private MultipartEntityBuilder entity = MultipartEntityBuilder.create();

    private JsonParsingStrategy<T> jsonParsingStrategy;

    private static final String FILE_PART_NAME = "attachment";
    private static final String STRING_PART_NAME = "text";

    /** Content type for request. */
    private static final String PROTOCOL_CONTENT_TYPE = "multipart/form-data";


    public CustomMultipartRequest(int requestMethod, String url, Map<String, String> params, Type type, Map<String, String> headers, byte[] byteArray, Class<T> clazz, boolean unWrap,
                                  JsonParsingStrategy<T> jsonParsingStrategy, Listener<T> listener, ErrorListener errorListener) {
        super(requestMethod, url, errorListener);
        this.params = params;
        this.clazz = clazz;
        this.type = type;
        this.listener = listener;
        this.byteArray = byteArray;
        this.jsonParsingStrategy = jsonParsingStrategy;
        this.gson = BaseApi.getGsonInstance();

        this.unWrap = unWrap;

        if(headers == null) {
            this.headers = new HashMap<String, String>();
            this.headers.put("Content-type", PROTOCOL_CONTENT_TYPE);
        } else {
            this.headers = headers;
        }

        buildMultipartEntity();

    }

    private void buildMultipartEntity()
    {
        try
        {

            for ( String key : params.keySet() ) {
                entity.addPart(key, new StringBody(params.get(key), ContentType.TEXT_PLAIN));
            }

            entity.addPart(FILE_PART_NAME, new ByteArrayBody(byteArray, ContentType.APPLICATION_OCTET_STREAM, "image.jpg"));

        }
        catch (Exception e)
        {
            VolleyLog.e("UnsupportedEncodingException");
        }
    }



    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }

    @Override
    public byte[] getBody() {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            httpentity = entity.build();
            httpentity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    public String getBodyContentType() {
        return httpentity.getContentType().getValue();
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