package com.malviya.blankframework.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.utils.Utils;

/**
 * Created by Admin on 24-12-2016.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageViewBack;
    private ImageView mImageViewProfile;
    private TextView mTextViewTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
    }

    private void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.imageview_back);
        mTextViewTitle = (TextView) findViewById(R.id.textview_title);
        mImageViewProfile = (ImageView) findViewById(R.id.imageview_profile);
        mImageViewProfile.setVisibility(View.GONE);
        mTextViewTitle.setText(getResources().getString(R.string.tab_profile));
        mImageViewBack.setVisibility(View.VISIBLE);
        mImageViewBack.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        finish();
        Utils.animLeftToRight(ProfileActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
        }
    }
}
