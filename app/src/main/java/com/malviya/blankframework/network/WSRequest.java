package com.malviya.blankframework.network;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.malviya.blankframework.R;
import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.utils.AppLog;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 26-11-2016.
 */

public class WSRequest {
    public final static int POST = Request.Method.POST;
    public final static int GET = Request.Method.GET;
    public final static int PUT = Request.Method.PUT;
    private static WSRequest mInstance;
    private int count;

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
        AppLog.networkLog(TAG, "--------------------------------------------------------------------------------");
        AppLog.networkLog(TAG, "URL: " + url);
        switch (method)
        {
            case GET:
                AppLog.networkLog(TAG, "method: GET " + method);
                break;
            case POST:
                AppLog.networkLog(TAG, "method: POST " + method);
                break;
            default:
                AppLog.networkLog(TAG, "method: " + method);
                break;
        }

        final StringRequest request = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.trim().length() > 0)
                    pWSRequest.onResponse(response);
                else
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Response is coming blank: " + response, Toast.LENGTH_SHORT).show();

                AppLog.networkLog(TAG, "Response: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pWSRequest.onErrorResponse(error);
                AppLog.networkLog(TAG, "Error: " + error.getMessage());
                if( error.networkResponse!=null){
                    AppLog.networkLog(TAG, "Error: statusCode: " + error.networkResponse.statusCode);
                }
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), MyApplication.getInstance().getApplicationContext().getString(R.string.msg_net_wrro) + error.getMessage(), Toast.LENGTH_SHORT).show();

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


            @Override
            public boolean isCanceled() {
                return super.isCanceled();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse resp) {
                if (resp.statusCode == 200) {
                    AppLog.networkLog("parseNetworkResponse", "" + resp.statusCode);
                    AppLog.networkLog("networkTimeMs", "" + resp.networkTimeMs);
                    AppLog.networkLog("notModified", "" + resp.notModified);
                    Map<String, String> mapHeader = resp.headers;
                    AppLog.networkLog("headers", "" + mapHeader);
                    if ((mapHeader.get(WSContant.TAG_TOKEN)).trim().length() > 0) {
                        UserInfo.authToken = mapHeader.get(WSContant.TAG_TOKEN);
                    }
                    UserInfo.tokenExp = mapHeader.get(WSContant.TAG_TOKEN_EXP);
                    UserInfo.tokenIssue = mapHeader.get(WSContant.TAG_TOKEN_ISSUE);
                    //parseData(resp.data);
                }
                return super.parseNetworkResponse(resp);
            }
        };
        if (TAG == null) {
            MyApplication.getInstance().addToRequestQueue(request, "");
        } else {
            MyApplication.getInstance().addToRequestQueue(request, TAG);
        }

    }

//    private String parseData(byte[] response) {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        try {
//            if (response!=null) {
//
//                //String content =request.responseHeaders.get("Content-Disposition").toString();
//                StringTokenizer st = new StringTokenizer("");
//                //String[] arrTag = st.toArray();
//
//                //String filename = arrTag[1];
//                //filename = filename.replace(":", ".");
//               // Log.d("DEBUG::FILE NAME", filename);
//
//                try{
//                    long lenghtOfFile = response.length;
//
//                    InputStream input = new ByteArrayInputStream(response);
//
//                    File path = Environment.getExternalStorageDirectory();
//                    File file = new File(path, "ddd");
//                    map.put("resume_path", file.toString());
//                    BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
//                    byte data[] = new byte[1024];
//
//                    long total = 0;
//                    count = 0;
//                    while ((count = input.read(data)) != -1) {
//                        total += count;
//                        output.write(data, 0, count);
//                    }
//
//                    output.flush();
//
//                    output.close();
//                    input.close();
//                }catch(IOException e){
//                    e.printStackTrace();
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
