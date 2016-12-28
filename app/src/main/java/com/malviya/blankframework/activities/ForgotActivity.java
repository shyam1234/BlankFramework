package com.malviya.blankframework.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.utils.Utils;

/**
 * Created by Admin on 11-12-2016.
 */
public class ForgotActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImageViewBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        initView();
    }

    private void initView() {
       // mImageViewBack = (ImageView) findViewById(R.id.imageview_back);
      //  mImageViewBack.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.imageview_back:
//                finish();
//                break;
//        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Utils.animLeftToRight(ForgotActivity.this);

    }
}
