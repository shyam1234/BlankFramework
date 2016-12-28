package com.malviya.blankframework.network;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.utils.AppLog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 26-11-2016.
 */

public class WSRequest {
    public static int POST = Request.Method.POST;
    public static int GET = Request.Method.GET;
    public static int PUT = Request.Method.PUT;
    private static WSRequest mInstance;

    private WSRequest() {

    }

    public static WSRequest getInstance() {
        if (mInstance == null)
            mInstance = new WSRequest();
        return mInstance;
    }

    public synchronized void request(int method, String url, String TAG, final IWSRequest pWSRequest) {
        StringRequest request = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pWSRequest.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pWSRequest.onErrorResponse(error);
            }
        });
        if (TAG == null) {
            MyApplication.getInstance().addToRequestQueue(request, "");
        } else {
            MyApplication.getInstance().addToRequestQueue(request, TAG);
        }

        //code for timeout
        //do
    }

    public synchronized void requestWithParam(int method, String url, final Map<String, String> pHeader, final Map<String, String> pParam, final String TAG, final IWSRequest pWSRequest) {
        AppLog.networkLog(TAG, "--------------------------------------------------------------------------------" );
        AppLog.networkLog(TAG, "URL: " + url);
        AppLog.networkLog(TAG, "method: " + method);
        final StringRequest request = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.trim().length() > 0)
                    pWSRequest.onResponse(response);
                else
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Response is coming blank: " + response, Toast.LENGTH_SHORT).show();

                AppLog.networkLog(TAG, "Response: "+response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pWSRequest.onErrorResponse(error);
                AppLog.networkLog(TAG, "Error: "+error.getMessage());
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Error Response: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = (pParam != null ? pParam : new HashMap<String, String>());
                AppLog.networkLog(TAG, "getParams: " + params.toString());
                return params;

            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = (pHeader != null ? pHeader : new HashMap<String, String>());
                AppLog.networkLog(TAG, "getHeaders: " + params.toString());
                return params;
            }


        };
        if (TAG == null) {
            MyApplication.getInstance().addToRequestQueue(request, "");
        } else {
            MyApplication.getInstance().addToRequestQueue(request, TAG);
        }

    }


}
