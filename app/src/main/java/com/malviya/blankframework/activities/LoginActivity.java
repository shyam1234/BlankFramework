package com.malviya.blankframework.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.malviya.blankframework.R;
import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.network.IWSRequest;
import com.malviya.blankframework.network.WSRequest;
import com.malviya.blankframework.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 03-12-2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextViewForgotPassword;
    private Button mButtonLogin;
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mEditTextUserName = (EditText) findViewById(R.id.edittext_email);
        mEditTextPassword = (EditText) findViewById(R.id.edittext_password);
        mTextViewForgotPassword = (TextView) findViewById(R.id.textview_forgot_password);
        mButtonLogin = (Button) findViewById(R.id.btnSignIn);
        setListner();
    }


    private void setListner() {
        mTextViewForgotPassword.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.textview_forgot_password:
                Intent intent = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(intent);
                Utils.animRightToLeft(LoginActivity.this);
                break;
            case R.id.btnSignIn:
                doLogin();
                break;
        }
    }

    private void doLogin() {
        if (Utils.validateUserName(mEditTextUserName) &&
                Utils.validatePassword(mEditTextPassword)) {

            mButtonLogin.setText(getResources().getString(R.string.proceeding));

            //call to WS and validate given credential----
            Map<String,String> header = new HashMap<>();
            header.put(WSContant.TAG_AUTHORIZATION, Utils.encodeToString(mEditTextUserName.getText().toString()+":"+mEditTextPassword.getText().toString()));
            WSRequest.getInstance().requestWithParam(WSRequest.GET, WSContant.URL_LOGIN, header, null, WSContant.TAG_LOGIN , new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    Intent i = new Intent(LoginActivity.this, NewDashboard.class);
                    startActivity(i);
                    Utils.animRightToLeft(LoginActivity.this);
                    mButtonLogin.setText(getResources().getString(R.string.sign_in));
                }

                @Override
                public void onErrorResponse(VolleyError response) {
                    mButtonLogin.setText(getResources().getString(R.string.sign_in));
                }
            });
            //------------------------------------------------

        }
    }
}
