package com.malviya.blankframework.network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.malviya.blankframework.application.MyApplication;

/**
 * Created by Admin on 26-11-2016.
 */

public class WSRequest {
    public static int POST = Request.Method.POST;
    public static int GET = Request.Method.GET;
    public static int PUT = Request.Method.PUT;
    private static  WSRequest mInstance;
    private  WSRequest(){

    }

    public static WSRequest getInstance(){
        if(mInstance==null)
        mInstance = new WSRequest();
        return mInstance;
    }
    public synchronized void request(int method, String url, String TAG, final IWSRequest pWSRequest){
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
        if(TAG==null) {
            MyApplication.getInstance().addToRequestQueue(request,"");
        }else {
            MyApplication.getInstance().addToRequestQueue(request, TAG);
        }

        //code for timeout
        //do
    }
}
