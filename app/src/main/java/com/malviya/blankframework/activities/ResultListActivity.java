//package com.malviya.blankframework.activities;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.malviya.blankframework.R;
//import com.malviya.blankframework.adapters.ResultListAdapter;
//import com.malviya.blankframework.models.ResultListDataModel;
//import com.malviya.blankframework.utils.GetPicassoImage;
//import com.malviya.blankframework.utils.UserInfo;
//import com.malviya.blankframework.utils.Utils;
//
//import java.util.ArrayList;
//
///**
// * Created by 23508 on 2/7/2017.
// */
//
//public class ResultListActivity extends AppCompatActivity implements View.OnClickListener {
//
//    private RecyclerView mRecycleViewResultList;
//    private ArrayList<ResultListDataModel> mResultList;
//    private ResultListAdapter mResultListAdapter;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_result_detail);
//
//        init();
//        initView();
//    }
//
//    private void init() {
//        mResultList = new ArrayList<ResultListDataModel>();
//        ResultListDataModel model = new ResultListDataModel();
//        model.setSemester("Semester 1");
//        mResultList.add(model);
//        model = new ResultListDataModel();
//        model.setSemester("Semester 2");
//        mResultList.add(model);
//        model = new ResultListDataModel();
//        model.setSemester("Semester 3");
//        mResultList.add(model);
//        model = new ResultListDataModel();
//        model.setSemester("Semester 4");
//        mResultList.add(model);
//        model = new ResultListDataModel();
//        model.setSemester("Semester 5");
//        mResultList.add(model);
//        model = new ResultListDataModel();
//        model.setSemester("Semester 6");
//        mResultList.add(model);
//        model = new ResultListDataModel();
//        model.setSemester("Semester 7");
//        mResultList.add(model);
//        model = new ResultListDataModel();
//        model.setSemester("Semester 8");
//        mResultList.add(model);
//    }
//
//    private void initView() {
//        //------------------------------------
//        TextView mTextViewTitle = (TextView) findViewById(R.id.textview_title);
//        mTextViewTitle.setText(R.string.tab_result);
//        ImageView mImgProfile = (ImageView) findViewById(R.id.imageview_profile);
//        mImgProfile.setVisibility(View.VISIBLE);
//        GetPicassoImage.setCircleImageByPicasso(this, UserInfo.selectedStudentImageURL, mImgProfile);
//        ImageView mImgBack = (ImageView) findViewById(R.id.imageview_back);
//        mImgBack.setVisibility(View.VISIBLE);
//        mImgBack.setOnClickListener(this);
//        //------------------------------------
//        initRecyclerView();
//    }
//
//    private void initRecyclerView() {
//        mRecycleViewResultList = (RecyclerView) findViewById(R.id.recyclerview_results_list);
//        mRecycleViewResultList.setHasFixedSize(true);
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        manager.setSmoothScrollbarEnabled(true);
//        mRecycleViewResultList.setLayoutManager(manager);
//        mResultListAdapter = new ResultListAdapter(this, mResultList, this);
//        mRecycleViewResultList.setAdapter(mResultListAdapter);
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//        Utils.animLeftToRight(ResultListActivity.this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.imageview_back:
//                onBackPressed();
//                break;
//            case R.id.imageview_resultlist_row_selection:
//                int position = (Integer) v.getTag();
//                ((ImageView) (v.findViewById(R.id.imageview_resultlist_row_selection))).setImageResource(R.drawable.selected);
//                //ResultFragment.selected_sem = mResultList.get(position).getSemester();
//                finish();
//                break;
//        }
//    }
//
//}
