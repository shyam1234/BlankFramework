package com.malviya.blankframework.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.malviya.blankframework.R;

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
        init();
    }

    private void init() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                //navigate to next page
                navigateToNextPage();
            }
        };
        mHandler.postDelayed(mRunnable, TIME_DELAY);
    }

    private void navigateToNextPage() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
