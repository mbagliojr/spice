package com.emmbi.mobile.spice.json;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

/**
 * Created by mbagliojr on 4/14/15.
 */
public abstract class RequestCallback<T> {

    public RequestCallback() {

    }

    public void updateUI(T response) {
        VolleyLog.v("Response:%n %s", response);

    }

    public void onServerFailure(VolleyError error) {
        VolleyLog.e("Error: ", error.getMessage());

        /*if(error.networkResponse.statusCode == 401 && mActivity != null) {

            PreferenceUtil.putString(mActivity, PreferenceKeys.LOGIN_USERNAME, null);
            PreferenceUtil.putString(mActivity, PreferenceKeys.LOGIN_PASSWORD, null);
            PreferenceUtil.putString(mActivity, PreferenceKeys.LOGIN_SUBDOMAIN, null);

            Intent launchSignup = new Intent(mActivity, LoginActivity.class);
            mActivity.startActivity(launchSignup);

            if(mActivity instanceof Activity) {
                ((Activity) mActivity).finish();
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
