package com.malviya.blankframework.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.malviya.blankframework.R;
import com.malviya.blankframework.adapters.WardChildRowAdapter;
import com.malviya.blankframework.database.CommonInfo;
import com.malviya.blankframework.database.TableStudentDetails;
import com.malviya.blankframework.exceptions.ModelException;
import com.malviya.blankframework.models.LoginDataHolder;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.models.TableStudentDetailsDataModel;
import com.malviya.blankframework.utils.AppLog;
import com.malviya.blankframework.utils.RenderImageByPicasso;
import com.malviya.blankframework.utils.UserInfo;

import java.util.ArrayList;

/**
 * Created by Admin on 24-12-2016.
 */

public class WardFragment extends Fragment implements View.OnClickListener {

    private ImageView mProfileImage;//
    private TextView mTextViewProfileHeaderName; //textview_profile_header_name
    private TextView mProfileHeaderLocation;  //textview_profile_header_location
    private ImageView mProfileView; //imageview_profile_eye
    private RecyclerView mRecycleViewChildInfo;
    private WardChildRowAdapter mChildAdapter;
    private ArrayList<TableStudentDetailsDataModel> mListChildInfoHolder;
    private ImageView mProfileEye;
    private FloatingActionButton mFloatingBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getAllChildWRTParent();
    }

    private void init() {
        mListChildInfoHolder = new ArrayList<TableStudentDetailsDataModel>();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_ward, null);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        mProfileImage = (ImageView) getView().findViewById(R.id.imageview_profile_logo);
        mTextViewProfileHeaderName = (TextView) getView().findViewById(R.id.textview_profile_header_name);
        mProfileHeaderLocation = (TextView) getView().findViewById(R.id.textview_profile_header_location);
        mProfileView = (ImageView) getView().findViewById(R.id.imageview_profile_logo);
        mProfileEye = (ImageView) getView().findViewById(R.id.imageview_profile_eye);
        mFloatingBtn = (FloatingActionButton)getView().findViewById(R.id.fab_edit);
        mProfileEye.setVisibility(View.VISIBLE);
        mRecycleViewChildInfo = (RecyclerView) getView().findViewById(R.id.listview_frag_ward);
        mProfileEye.setOnClickListener(this);
        mFloatingBtn.setOnClickListener(this);
        initRecycleAdapter();
        setDefaultStudentProfileInHeader(0);
    }

    private void setDefaultStudentProfileInHeader(int position) {
        RenderImageByPicasso.setCircleImageByPicasso(getContext(),mListChildInfoHolder.get(position).getImageurl(), mProfileImage);
        mTextViewProfileHeaderName.setText(mListChildInfoHolder.get(position).getStudent_name());
        mProfileHeaderLocation.setText(mListChildInfoHolder.get(position).getCourse());
        AppLog.log("setDefaultStudentProfileInHeader mTextViewProfileHeaderName ",mListChildInfoHolder.get(position).getCourse());
        AppLog.log("setDefaultStudentProfileInHeader mProfileHeaderLocation ",mListChildInfoHolder.get(position).getStudent_name());
    }

    private void initRecycleAdapter() {
        RecyclerView.LayoutManager linear = new LinearLayoutManager(getContext());
        mRecycleViewChildInfo.setLayoutManager(linear);
        mRecycleViewChildInfo.setItemAnimator(new DefaultItemAnimator());
        mChildAdapter = new WardChildRowAdapter(getContext(), mListChildInfoHolder, this);
        mRecycleViewChildInfo.setAdapter(mChildAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_profile_eye:
                Toast.makeText(getContext(), "Coming soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab_edit:
                Toast.makeText(getContext(), "Coming soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rel_ward_row_holder:
                setDefaultStudentProfileInHeader((Integer)view.getTag());
                break;
        }
    }



    /**
     * get all child with respect to logged parent
     */
    public void getAllChildWRTParent() {
        CommonInfo table = new CommonInfo();
        table.openDB(getContext());
        try {
           // mListChildInfoHolder =  table.getAllChildWRTParent(""+((LoginDataHolder)ModelFactory.getInstance().getModel(ModelFactory.MODEL_LOGIN)).data.UserId);
            mListChildInfoHolder =  table.getAllChildWRTParent(UserInfo.parentId);
        } catch (Exception e) {
            AppLog.errLog("getAllChildWRTParent",e.getMessage());
        }
        table.closeDB();
    }
}

