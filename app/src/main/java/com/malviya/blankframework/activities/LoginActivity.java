package com.malviya.blankframework.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.malviya.blankframework.R;

/**
 * Created by Admin on 03-12-2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextViewForgotPassword;
    private Button mButtonLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mTextViewForgotPassword = (TextView)findViewById(R.id.textview_forgot_password);
        mTextViewForgotPassword.setOnClickListener(this);
        mButtonLogin = (Button) findViewById(R.id.btnSignIn);
        mButtonLogin.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.textview_forgot_password:
                Intent intent = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSignIn:
                Intent i = new Intent(LoginActivity.this, NewDashboard.class);
                startActivity(i);
                break;
        }
    }
}
