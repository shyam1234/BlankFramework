package com.malviya.blankframework.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.malviya.blankframework.R;
import com.malviya.blankframework.utils.Utils;

/**
 * Created by Admin on 11-12-2016.
 */
public class SplashActivity extends AppCompatActivity {

    private static final long TIME_DELAY = 3000;
    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Utils.animRightToLeft(SplashActivity.this);
        init();
    }

    private void init() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                navigateToNextPage();
            }
        };
        mHandler.postDelayed(mRunnable, TIME_DELAY);
        //------------------------------------------
        checkForLanguage();
    }

    private void checkForLanguage() {

    }

    private void navigateToNextPage() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        Utils.animRightToLeft(SplashActivity.this);
    }


}
