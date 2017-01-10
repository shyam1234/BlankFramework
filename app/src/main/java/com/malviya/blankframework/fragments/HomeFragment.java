package com.malviya.blankframework.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.malviya.blankframework.R;
import com.malviya.blankframework.adapters.DashboardCellAdapter;
import com.malviya.blankframework.models.DashboardCellDataHolder;

import java.util.ArrayList;

/**
 * Created by Admin on 24-12-2016.
 */

public class HomeFragment extends Fragment {
    private Handler mHandlerNavigationToDetail;
    private GridView mGridViewCell;
    private DashboardCellAdapter mAdapter;
    private ArrayList<DashboardCellDataHolder> mCellList;

    public HomeFragment(Handler pHandlerNavigationToDetail) {
        this.mHandlerNavigationToDetail = pHandlerNavigationToDetail;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mCellList = new ArrayList<>();
        addCellItem();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_home, null);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    private void initView() {
        mAdapter = new DashboardCellAdapter(getContext(), mCellList);
        mGridViewCell = (GridView) getView().findViewById(R.id.gridview_dashboard);
        mGridViewCell.setAdapter(mAdapter);
        mGridViewCell.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(getContext(), "position " + i, Toast.LENGTH_SHORT).show();
                //mHandlerNavigationToDetail.sendMessage(mHandlerNavigationToDetail.obtainMessage(0, i));
                navigateFragment(new NoticeboardFragment());
            }
        });
    }


    private void addCellItem() {
        DashboardCellDataHolder holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.noticeboard);
        holder.setText("Notice Board");
        holder.setNotification("33");
        holder.setColor(R.color.colorLightGreen);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.attendance);
        holder.setText("Attendance");
        holder.setNotification("3");
        holder.setColor(R.color.colorLightYellow);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.homework);
        holder.setNotification("1");
        holder.setText("Homework");
        holder.setColor(R.color.colorLightWallet);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.diary);
        holder.setNotification("1");
        holder.setText("Diary");
        holder.setColor(R.color.colorDarkBlue);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.messages);
        holder.setNotification("1");
        holder.setText("Message");
        holder.setColor(R.color.colorSkyBlue);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.events);
        holder.setNotification("1");
        holder.setText("Events");
        holder.setColor(R.color.colorDarkYellow);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.gallery);
        holder.setNotification("1");
        holder.setText("Gallery");
        holder.setColor(R.color.colorLightVallet);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.fee);
        holder.setNotification("1");
        holder.setText("Feedback");
        holder.setColor(R.color.colorLightRed);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.fee);
        holder.setNotification("1");
        holder.setText("Fee");
        holder.setColor(R.color.colorLightGray);
        mCellList.add(holder);

    }


    private void navigateFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.linearlayout, fragment);
        ft.commit();
        //ft.setCustomAnimations(R.anim.left, R.anim.right);
        Toast.makeText(getContext(), "position +++ ", Toast.LENGTH_SHORT).show();
    }
}
