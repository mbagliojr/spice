package com.emmbi.mobile.datacache.json;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

/**
 * Created by mbagliojr on 4/14/15.
 */
public abstract class RequestCallback<T> {

    private Context mContext;

    public RequestCallback(Context context) {
        mContext = context;
    }

    public void updateUI(T response) {
        VolleyLog.v("Response:%n %s", response);

    }

    public void onServerFailure(VolleyError error) {
        VolleyLog.e("Error: ", error.getMessage());

        /*if(error.networkResponse.statusCode == 401 && mContext != null) {

            PreferenceUtil.putString(mContext, PreferenceKeys.LOGIN_USERNAME, null);
            PreferenceUtil.putString(mContext, PreferenceKeys.LOGIN_PASSWORD, null);
            PreferenceUtil.putString(mContext, PreferenceKeys.LOGIN_SUBDOMAIN, null);

            Intent launchSignup = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(launchSignup);

            if(mContext instanceof Activity) {
                ((Activity) mContext).finish();
            }
        }*/
    }


    public Response.Listener<T> getSuccessListener() {
        return new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                updateUI(response);
            }
        };
    }

    public Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onServerFailure(error);
            }
        };
    }

}
