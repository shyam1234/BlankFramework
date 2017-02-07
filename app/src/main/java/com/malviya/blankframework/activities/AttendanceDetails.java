package com.malviya.blankframework.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.malviya.blankframework.R;

/**
 * Created by 23508 on 2/7/2017.
 */

public class AttendanceDetails extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_details);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
